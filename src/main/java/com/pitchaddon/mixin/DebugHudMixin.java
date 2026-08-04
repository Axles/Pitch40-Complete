package com.pitchaddon.mixin;

import com.pitchaddon.modules.CustomPitch40;
import meteordevelopment.meteorclient.systems.modules.Modules;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.DebugHud;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(DebugHud.class)
public class DebugHudMixin {

    @Inject(method = "getLeftText", at = @At("RETURN"))
    private void onGetLeftText(CallbackInfoReturnable<List<String>> cir) {
        CustomPitch40 module = Modules.get().get(CustomPitch40.class);
        if (module == null || !module.isActive() || !module.showSpeedInF3.get()) return;

        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc.player == null) return;

        Vec3d vel = mc.player.getVelocity();
        double horizontal = Math.sqrt(vel.x * vel.x + vel.z * vel.z) * 20.0;
        double vertical = vel.y * 20.0;
        double total = Math.sqrt(vel.x * vel.x + vel.y * vel.y + vel.z * vel.z) * 20.0;

        List<String> list = cir.getReturnValue();
        list.add("");
        list.add(String.format("Speed: %.2f b/s (H: %.2f  V: %.2f)", total, horizontal, vertical));
    }
}