package me.kodingking.kodax.mixins.render;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.util.EnumWorldBlockLayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderGlobal.class)
public class MixinRenderGlobal {

//  @Inject(method = "renderBlockLayer(Lnet/minecraft/util/EnumWorldBlockLayer;)V", at = @At("HEAD"))
//  private void preRenderBlockLayer(EnumWorldBlockLayer blockLayerIn, CallbackInfo callbackInfo) {
//    GlStateManager.enableAlpha();
//    GlStateManager.enableCull();
//    GlStateManager.enableDepth();
//    GlStateManager.depthMask(true);
//  }

}
