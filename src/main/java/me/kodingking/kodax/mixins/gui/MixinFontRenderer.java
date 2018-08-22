package me.kodingking.kodax.mixins.gui;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FontRenderer.class)
public class MixinFontRenderer {

    @Inject(method = "renderString", at = @At("HEAD"))
    private void preRenderString(String text, float x, float y, int color, boolean dropShadow, CallbackInfoReturnable<Integer> callbackInfoReturnable) {
        GlStateManager.enableAlpha();
    }

    @Inject(method = "renderString", at = @At("RETURN"))
    private void postRenderString(String text, float x, float y, int color, boolean dropShadow, CallbackInfoReturnable<Integer> callbackInfoReturnable) {
        GlStateManager.disableAlpha();
    }

}
