# Pitch40 Addon for Meteor Client (1.21.4)

A Meteor Client addon that adds **CustomPitch40** — a fully configurable version of Meteor’s Pitch40 elytra flight mode.

## Features

- **Descending Pitch** – Change the pitch used while gliding down (default 37.72°)
- **Ascending Pitch** – Change the pitch used while climbing (default -54.77°)
- **Lower / Upper Bounds** – Same height control as vanilla Pitch40
- **Rotation Speeds** – Adjust how fast pitch changes up and down
- **Randomize Speed** – Optional slight randomization (matches original behavior)
- Works independently of Meteor’s built-in ElytraFly Pitch40 mode

## Installation

1. Install [Fabric+ Loader]([https://fabricmc.net/](https://modrinth.com/mod/viafabricplus/version/4.0.5-BACKPORT)) for Minecraft **1.21.4**
2. Install [Meteor Client]([https://meteorclient.com/](https://meteorclient.com/api/download?version=1.21.4)) for 1.21.4
3. Place `pitch40-addon-1.0.0.jar` into your `.minecraft/mods` folder
4. Launch the game

## Usage

1. Open Meteor GUI (Right Shift by default)
2. Go to the **Pitch40** category
3. Enable **Custom Pitch40**
4. Adjust the pitch values, bounds, and speeds to your liking

**Important:** Start above the upper bounds (and ideally ≥40 blocks above the lower bounds) or disable the “Check Bounds on Activate” setting.

## Building from Source

```bash
./gradlew build
```

The jar will be in `build/libs/`.

## Notes

- This module sets your look pitch every tick while gliding. It does not replace Meteor’s ElytraFly module — you can use it alone or together with other flight helpers.
- Recommended to turn **off** Meteor’s own ElytraFly Pitch40 mode while using this to avoid conflicting pitch control.
