package me.kodingking.kodax.mixins.gui;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public class MixinGui {

    @Inject(method = "drawModalRectWithCustomSizedTexture", at = @At("HEAD"))
    private static void preDrawModalRectWithCustomSizedTexture(int x, int y, float u, float v, int width, int height, float textureWidth, float textureHeight, CallbackInfo callbackInfo) {
        GlStateManager.enableAlpha();
    }

    @Inject(method = "drawModalRectWithCustomSizedTexture", at = @At("RETURN"))
    private static void postDrawModalRectWithCustomSizedTexture(int x, int y, float u, float v, int width, int height, float textureWidth, float textureHeight, CallbackInfo callbackInfo) {
        GlStateManager.disableAlpha();
    }

    @Inject(method = "drawTexturedModalRect(IIIIII)V", at = @At("HEAD"))
    private void preDrawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height, CallbackInfo callbackInfo) {
        GlStateManager.enableAlpha();
    }

    @Inject(method = "drawTexturedModalRect(IIIIII)V", at = @At("RETURN"))
    private void postDrawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height, CallbackInfo callbackInfo) {
        GlStateManager.disableAlpha();
    }

}
