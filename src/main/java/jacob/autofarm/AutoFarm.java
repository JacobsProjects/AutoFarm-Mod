package jacob.autofarm;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AutoFarm implements ModInitializer {
	public static final String MOD_ID = "autofarm";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static boolean enabled = false;

	@Override
	public void onInitialize() {
		LOGGER.info("AutoFarm initialized!");
	}
}
