package me.kodingking.kodax.mixins.gui;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FontRenderer.class)
public class MixinFontRenderer {

    @Inject(method = "drawString(Ljava/lang/String;FFIZ)I", at = @At("HEAD"))
    private void preDrawString(String text, float x, float y, int color, boolean dropShadow, CallbackInfoReturnable<Float> callbackInfo) {
        GlStateManager.pushAttrib();
        GlStateManager.pushMatrix();
        GlStateManager.enableAlpha();
    }

    @Inject(method = "drawString(Ljava/lang/String;FFIZ)I", at = @At("RETURN"))
    private void postDrawString(String text, float x, float y, int color, boolean dropShadow, CallbackInfoReturnable<Float> callbackInfo) {
        GlStateManager.popAttrib();
        GlStateManager.popMatrix();
        GlStateManager.disableAlpha();
    }

}
