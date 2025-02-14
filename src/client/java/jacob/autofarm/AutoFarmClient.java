package jacob.autofarm;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.EntityHitResult;

import static com.mojang.text2speech.Narrator.LOGGER;

public class AutoFarmClient implements ClientModInitializer {
	private static final MinecraftClient client = MinecraftClient.getInstance();
	private long lastHit;
	private boolean isEating = false;
	private int previousSlot = -1;
	private long startEatingTime = 0;

	@Override
	public void onInitializeClient() {
		Keybinds.register();

		// Register the tick event
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (AutoFarm.enabled && client.player != null) {
				checkHealth();
				if (!isEating) {
					checkAutoAttack();
				}
				checkHunger();
			}
		});

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (Keybinds.openMenuKey.wasPressed()) {
				toggleMod();
			}
		});

		LOGGER.info("AutoFarm Client Initialized!");
	}

	public static void toggleMod() {
		AutoFarm.enabled = !AutoFarm.enabled;
		if (client.player != null) {
			client.player.sendMessage(Text.of("AutoFarm " + (AutoFarm.enabled ? "enabled" : "disabled")), true);
		}
	}

	private void checkHealth() {
		if (client.player.getHealth() <= Config.logoutHealth) {
			client.player.networkHandler.getConnection().disconnect(Text.of("AutoLogout triggered, you were at " + Config.logoutHealth +  " HP!"));
			AutoFarm.enabled = false;
		}
	}

	private void checkAutoAttack() {
		long currentTime = System.currentTimeMillis();
		long timeSinceAttack = currentTime - lastHit;
		if (client.player.getAttackCooldownProgress(0) >= 1.0F && timeSinceAttack >= Config.swingDelay * 50) {
			if (client.crosshairTarget instanceof EntityHitResult entityHit) {
				client.interactionManager.attackEntity(client.player, entityHit.getEntity());
				lastHit = currentTime;
			}
		}
	}

	private void checkHunger() {
		if (client.player == null) return;

		if (isEating) {
			if (System.currentTimeMillis() - startEatingTime >= 1600) {
				finishEating();
				return;
			}

			client.options.useKey.setPressed(true);
			return;
		}

		if (client.player.getHungerManager().getFoodLevel() <= Config.eatHunger) {
			int foodSlot = findFoodInHotbar();
			if (foodSlot != -1) {
				startEating(foodSlot);
			}
		}
	}

	private void startEating(int foodSlot) {
		if (client.player == null) return;

		previousSlot = client.player.getInventory().selectedSlot;
		client.player.getInventory().selectedSlot = foodSlot;
		isEating = true;
		startEatingTime = System.currentTimeMillis();
		client.options.useKey.setPressed(true);

		// Log for debugging
		LOGGER.info("Started eating from slot " + foodSlot);
	}

	private void finishEating() {
		if (client.player == null) return;

		client.options.useKey.setPressed(false);

		if (previousSlot != -1) {
			client.player.getInventory().selectedSlot = previousSlot;
			previousSlot = -1;
		}

		isEating = false;

		// Log for debugging
		LOGGER.info("Finished eating");
	}

	private int findFoodInHotbar() {
		if (client.player == null) return -1;

		for (int i = 0; i < 9; i++) {
			ItemStack stack = client.player.getInventory().getStack(i);
			if (!stack.isEmpty() && stack.getItem().getComponents().contains(DataComponentTypes.FOOD)) {
				return i;
			}
		}
		return -1;
	}
}
