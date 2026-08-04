package com.pitchaddon;

import com.mojang.logging.LogUtils;
import com.pitchaddon.modules.CustomPitch40;
import meteordevelopment.meteorclient.addons.MeteorAddon;
import meteordevelopment.meteorclient.systems.modules.Category;
import meteordevelopment.meteorclient.systems.modules.Modules;
import org.slf4j.Logger;

public class PitchAddon extends MeteorAddon {
    public static final Logger LOG = LogUtils.getLogger();
    public static final Category CATEGORY = new Category("Rutahns Pitch40");

    @Override
    public void onInitialize() {
        LOG.info("Initializing Pitch40 Addon");
        Modules.get().add(new CustomPitch40());
    }

    @Override
    public void onRegisterCategories() {
        Modules.registerCategory(CATEGORY);
    }

    @Override
    public String getPackage() {
        return "com.pitchaddon";
    }
}
