
# AutoFarm Mod

A simple Fabric mod for Minecraft that automates common tasks like auto-swinging, auto-eating, and auto-logging out based on health. Perfect for use in an SMP server or solo play!

## Features

- **Auto-swing**: Automatically swings the player's weapon when ready to attack.
- **Auto-eat**: Automatically eats food when the player's hunger is below a threshold.
- **Auto-logout**: Logs out automatically if health falls below a set threshold.
- **Customizable**: All settings are adjustable through a simple in-game menu.

## Installation

1. Install **Fabric** and **Fabric API**.
2. Download the mod's `.jar` file from the releases page (or clone this repo and build it yourself).
3. Place the `.jar` file in the `mods` folder of your Minecraft setup.
4. Launch Minecraft with Fabric loader.

## Configuration

The mod includes an in-game menu for configuration. The available settings are:

- **Logout Health**: The health level at which the player will automatically log out (in hearts).
- **Swing Delay**: The delay between auto swings (in ticks, max 50).
- **Eat Hunger**: The hunger level at which the player will automatically eat food.

Use the in-game menu to adjust these values to your liking.

## Controls

- **Toggle Mod**: Press the configured key to toggle the mod on or off.
- **Menu**: Open the mod settings menu with the assigned key.

## Keybindings

You can customize the keybindings for toggling the mod and opening the menu. This is set within the Minecraft options under **Controls**.

## Usage

1. When enabled, the mod will automatically:
   - Attack nearby enemies when the player's attack cooldown is ready.
   - Eat food if hunger reaches the defined threshold.
   - Log out if health is below the specified level.
   
2. All actions can be customized via the in-game menu.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
