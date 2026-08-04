# Pitch40 Addon for Meteor Client (1.21.4) FOR 2b2t.org Minecraft server! Working 8-4-2026

A Meteor Client addon that adds **CustomPitch40** — a fully configurable version of Meteor’s Pitch40 elytra flight mode.

## Features

- **Descending Pitch** – Change the pitch used while gliding down (default 37.72°)
- **Ascending Pitch** – Change the pitch used while climbing (default -54.77°)
- **Lower / Upper Bounds** – Same height control as vanilla Pitch40
- **Rotation Speeds** – Adjust how fast pitch changes up and down
- **Randomize Speed** – Optional slight randomization (matches original behavior)
- Works independently of Meteor’s built-in ElytraFly Pitch40 mode. DO NOT Use them while using this custom mode.

## Installation

1. Install [Fabric+ Loader](https://cdn.modrinth.com/data/rIC2XJV4/versions/8sPJsNcy/ViaFabricPlus-4.0.5-BACKPORT.jar?mr_download_reason=standalone) for Minecraft **1.21.4**
2. Install [Meteor Client](https://meteorclient.com/api/download?version=1.21.4) for 1.21.4
3. Place `pitch40-addon-1.0.0.jar` from the build/libs folder into your `.minecraft/mods` folder
4. Launch the game
5. Select Multiplayer and click "ViaFabric" button and switch your version to 1.20.2
6. Click the back arrow and join 2b2t as usuall

## Usage

1. Open Meteor GUI (Right Shift by default)
2. Search **Custom Pitch40**
3. Right click to Adjust the pitch values, bounds, and speeds to your liking. Left click to enable. Keybinding available.
4. Bind to a key and enjoy!

**Important:** Start above the upper bounds (and ideally ≥40 blocks above the lower bounds) or disable the “Check Bounds on Activate” setting.

## Building from Source

```bash
./gradlew build
```

The jar will be in `build/libs/`.

## Notes

- This module sets your look pitch every tick while gliding. It does not replace Meteor’s ElytraFly module — you can use it alone or together with other flight helpers.
- Recommended to turn **off** Meteor’s own ElytraFly Pitch40 mode while using this to avoid conflicting pitch control.
