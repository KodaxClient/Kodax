package me.kodingking.kodax.mixins.render;

import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(GlStateManager.class)
public class MixinGlStateManager {

  /**
   * @author KodingKing
   */
  @Overwrite
  public static void clear(int mask) {
    GL11.glClear(mask);
  }
}
