package me.kodingking.kodax.mixins.render;

import me.kodingking.kodax.event.EventBus;
import me.kodingking.kodax.event.events.render.RenderOverlayEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderer.class)
public abstract class MixinEntityRenderer {

    @Inject(method = "renderWorldPass", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;disableAlpha()V", shift = Shift.AFTER))
    private void preRenderWorldPass(int pass, float partialTicks, long finishTimeNano,
                                    CallbackInfo callbackInfo) {
        GlStateManager.depthMask(true);
        GlStateManager.enableDepth();
        GlStateManager.enableAlpha();
    }

    @Inject(method = "renderWorldPass", at = @At("RETURN"))
    private void postRenderWorldPass(int pass, float partialTicks, long finishTimeNano,
                                     CallbackInfo callbackInfo) {
        GlStateManager.depthMask(false);
        GlStateManager.disableDepth();
        GlStateManager.enableAlpha();
    }

    @Inject(method = "updateCameraAndRender", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiIngame;renderGameOverlay(F)V", shift = At.Shift.BEFORE))
    private void preRenderGameOverlay(float partialTicks, long nanoTime, CallbackInfo callbackInfo) {
        RenderOverlayEvent renderOverlayEvent = new RenderOverlayEvent(
                new ScaledResolution(Minecraft.getMinecraft()), partialTicks, RenderOverlayEvent.Type.PRE);
        EventBus.call(renderOverlayEvent);
    }

    @Inject(method = "updateCameraAndRender", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiIngame;renderGameOverlay(F)V", shift = At.Shift.AFTER))
    private void postRenderGameOverlay(float partialTicks, long nanoTime, CallbackInfo callbackInfo) {
        RenderOverlayEvent renderOverlayEvent = new RenderOverlayEvent(
                new ScaledResolution(Minecraft.getMinecraft()), partialTicks, RenderOverlayEvent.Type.POST);
        EventBus.call(renderOverlayEvent);
    }

}
