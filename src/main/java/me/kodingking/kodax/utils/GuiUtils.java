package me.kodingking.kodax.utils;

import java.awt.Color;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiUtils {

  public static void drawBG() {
    ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());

    GL11.glEnable(GL11.GL_BLEND);
    GL11.glDisable(GL11.GL_DEPTH_TEST);
    GL11.glDepthMask(false);
    GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    GL11.glDisable(GL11.GL_ALPHA_TEST);

    Minecraft.getMinecraft().getTextureManager()
        .bindTexture(new ResourceLocation("textures/splash_background.png"));
    Gui.drawModalRectWithCustomSizedTexture(0, 0, 0, 0, sr.getScaledWidth(), sr.getScaledHeight(),
        sr.getScaledWidth(), sr.getScaledHeight());
  }

  public static void drawIngameGuiGradient() {
    ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
    Gui.drawRect(0, 0, sr.getScaledWidth(), sr.getScaledHeight(), new Color(0, 0, 0, 120).getRGB());
  }

  public static void applyShader(ResourceLocation path) {
    Method method = ReflectionUtils
        .getMethod(EntityRenderer.class, new String[]{"loadShader", "func_148057_a"},
            new Class[]{ResourceLocation.class});
    try {
      method.invoke(Minecraft.getMinecraft().entityRenderer,
          path);
    } catch (IllegalAccessException | InvocationTargetException e) {
      e.printStackTrace();
    }
  }

  public static void unloadShader() {
    Minecraft.getMinecraft().entityRenderer.stopUseShader();
  }

}
