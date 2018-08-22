package me.kodingking.kodax.mixins.render;

import me.kodingking.kodax.Kodax;
import me.kodingking.kodax.events.render.RenderOverlayEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.EntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderer.class)
public class MixinEntityRenderer {

  @Inject(method = "updateCameraAndRender", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiIngame;renderGameOverlay(F)V", shift = At.Shift.BEFORE))
  private void preRenderGameOverlay(float partialTicks, long nanoTime, CallbackInfo callbackInfo) {
    RenderOverlayEvent renderOverlayEvent = new RenderOverlayEvent(
        new ScaledResolution(Minecraft.getMinecraft()), partialTicks, RenderOverlayEvent.Type.PRE);
    Kodax.EVENT_BUS.call(renderOverlayEvent);
  }

  @Inject(method = "updateCameraAndRender", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiIngame;renderGameOverlay(F)V", shift = At.Shift.AFTER))
  private void postRenderGameOverlay(float partialTicks, long nanoTime, CallbackInfo callbackInfo) {
    RenderOverlayEvent renderOverlayEvent = new RenderOverlayEvent(
        new ScaledResolution(Minecraft.getMinecraft()), partialTicks, RenderOverlayEvent.Type.POST);
    Kodax.EVENT_BUS.call(renderOverlayEvent);
  }

}
