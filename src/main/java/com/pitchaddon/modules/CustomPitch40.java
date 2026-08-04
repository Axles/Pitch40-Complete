package com.pitchaddon.modules;

import com.pitchaddon.PitchAddon;
import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.settings.*;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Items;

public class CustomPitch40 extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();
    private final SettingGroup sgPitch = settings.createGroup("Pitch Values");
    private final SettingGroup sgBounds = settings.createGroup("Height Bounds");
    private final SettingGroup sgRotation = settings.createGroup("Rotation Speed");

    // General
    private final Setting<Boolean> requireElytra = sgGeneral.add(new BoolSetting.Builder()
        .name("require-elytra")
        .description("Only control pitch while gliding with an elytra.")
        .defaultValue(true)
        .build()
    );

    private final Setting<Boolean> autoToggleOnBounds = sgGeneral.add(new BoolSetting.Builder()
        .name("check-bounds-on-activate")
        .description("Disable the module if you are not in a valid height range when enabling.")
        .defaultValue(true)
        .build()
    );

    // Pitch values (the main feature)
    private final Setting<Double> descendingPitch = sgPitch.add(new DoubleSetting.Builder()
        .name("descending-pitch")
        .description("Target pitch while descending (vanilla Pitch40 uses ~37.72). Positive = looking down.")
        .defaultValue(37.72)
        .range(-90.0, 90.0)
        .sliderRange(-90.0, 90.0)
        .build()
    );

    private final Setting<Double> ascendingPitch = sgPitch.add(new DoubleSetting.Builder()
        .name("ascending-pitch")
        .description("Target pitch while ascending (vanilla Pitch40 uses ~-54.77). Negative = looking up.")
        .defaultValue(-54.77)
        .range(-90.0, 90.0)
        .sliderRange(-90.0, 90.0)
        .build()
    );

    // Height bounds
    private final Setting<Double> lowerBounds = sgBounds.add(new DoubleSetting.Builder()
        .name("lower-bounds")
        .description("Y level where the module starts pitching up. You should start at least ~40 blocks above this.")
        .defaultValue(180.0)
        .min(-64.0)
        .sliderMax(400.0)
        .build()
    );

    private final Setting<Double> upperBounds = sgBounds.add(new DoubleSetting.Builder()
        .name("upper-bounds")
        .description("Y level where the module starts pitching down.")
        .defaultValue(220.0)
        .min(-64.0)
        .sliderMax(400.0)
        .build()
    );

    // Rotation speeds
    private final Setting<Double> rotateSpeedUp = sgRotation.add(new DoubleSetting.Builder()
        .name("rotate-speed-up")
        .description("Degrees per tick when pitching upwards.")
        .defaultValue(5.45)
        .min(0.1)
        .sliderMax(20.0)
        .build()
    );

    private final Setting<Double> rotateSpeedDown = sgRotation.add(new DoubleSetting.Builder()
        .name("rotate-speed-down")
        .description("Degrees per tick when pitching downwards.")
        .defaultValue(0.90)
        .min(0.1)
        .sliderMax(5.0)
        .build()
    );

    private final Setting<Boolean> randomize = sgRotation.add(new BoolSetting.Builder()
        .name("randomize-speed")
        .description("Slightly randomize rotation speed each tick (matches vanilla Pitch40 behavior).")
        .defaultValue(true)
        .build()
    );

    // State
    private boolean pitchingDown = true;
    private float currentPitch;

    public CustomPitch40() {
        super(PitchAddon.CATEGORY, "custom-pitch40", "Pitch40-style elytra flight with fully configurable pitch angles.");
    }

    @Override
    public void onActivate() {
        if (mc.player == null) return;

        if (autoToggleOnBounds.get()) {
            if (mc.player.getY() < upperBounds.get()) {
                error("Player must be above upper bounds (%.1f)!", upperBounds.get());
                toggle();
                return;
            }
            if (mc.player.getY() - 40 < lowerBounds.get()) {
                error("Player must be at least 40 blocks above the lower bounds (%.1f)!", lowerBounds.get());
                toggle();
                return;
            }
        }

        pitchingDown = true;
        currentPitch = descendingPitch.get().floatValue();
        info("CustomPitch40 enabled. Descending pitch: %.2f | Ascending pitch: %.2f",
            descendingPitch.get(), ascendingPitch.get());
    }

    @Override
    public void onDeactivate() {
        // nothing special
    }

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        if (mc.player == null || mc.level == null) return;

        if (requireElytra.get()) {
            boolean gliding = mc.player.isFallFlying();
            boolean hasElytra = mc.player.getItemBySlot(EquipmentSlot.CHEST).is(Items.ELYTRA);
            if (!gliding || !hasElytra) return;
        }

        // Switch direction based on height bounds
        if (pitchingDown && mc.player.getY() <= lowerBounds.get()) {
            pitchingDown = false;
        } else if (!pitchingDown && mc.player.getY() >= upperBounds.get()) {
            pitchingDown = true;
        }

        float targetDesc = descendingPitch.get().floatValue();
        float targetAsc = ascendingPitch.get().floatValue();

        if (!pitchingDown) {
            // Pitching up (towards ascendingPitch, which is more negative)
            float step = rotateSpeedUp.get().floatValue();
            if (randomize.get()) {
                step = randPitch(step, 1.0f);
            }
            currentPitch -= step;

            if (currentPitch < targetAsc) {
                currentPitch = targetAsc;
                pitchingDown = true;
            }
        } else {
            // Pitching down (towards descendingPitch)
            if (currentPitch < targetDesc) {
                float step = rotateSpeedDown.get().floatValue();
                if (randomize.get()) {
                    step = randPitch(step, 0.5f);
                }
                currentPitch += step;
                if (currentPitch > targetDesc) {
                    currentPitch = targetDesc;
                }
            } else {
                currentPitch = targetDesc;
            }
        }

        // Clamp for safety
        currentPitch = Math.max(-90f, Math.min(90f, currentPitch));
        mc.player.setXRot(currentPitch);
    }

    /**
     * Create a random pitch offset around the given value (same approach as vanilla Pitch40).
     */
    private float randPitch(float value, float bound) {
        return (float) (value + (bound * (Math.random() - 0.5)));
    }
}
