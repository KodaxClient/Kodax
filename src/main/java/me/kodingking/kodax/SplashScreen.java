package me.kodingking.kodax;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class SplashScreen {

    private static ResourceLocation backgroundLocation = new ResourceLocation("textures/splash_background.png");
    private static ResourceLocation logoLocation = new ResourceLocation("textures/logo.png");

    public static class Progress {

        public int current;
        public int max = 11;

        public void inc(String text) {
            current++;
        }

        public int getCurrent() {
            return current;
        }

        public int getMax() {
            return max;
        }
    }

    private static Progress progress = new Progress();

    public static void advanceProgress(String text) {
        progress.inc(text);
        render(Minecraft.getMinecraft().getTextureManager());
    }

    public static void render(TextureManager tm) {
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        int i = sr.getScaleFactor();
        Framebuffer framebuffer = new Framebuffer(sr.getScaledWidth() * i, sr.getScaledHeight() * i, true);
        framebuffer.bindFramebuffer(false);
        GlStateManager.matrixMode(5889);
        GlStateManager.loadIdentity();
        GlStateManager.ortho(0.0D, (double) sr.getScaledWidth(), (double) sr.getScaledHeight(), 0.0D, 1000.0D, 3000.0D);
        GlStateManager.matrixMode(5888);
        GlStateManager.loadIdentity();
        GlStateManager.translate(0.0F, 0.0F, -2000.0F);
        GlStateManager.disableLighting();
        GlStateManager.disableFog();
        GlStateManager.disableDepth();
        GlStateManager.enableTexture2D();

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        tm.bindTexture(backgroundLocation);
        Gui.drawModalRectWithCustomSizedTexture(0, 0, 0, 0, sr.getScaledWidth(), sr.getScaledHeight(), sr.getScaledWidth(), sr.getScaledHeight());

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDisable(GL11.GL_ALPHA_TEST);

        tm.bindTexture(logoLocation);

        double reduceBy = 5;

        double w = Constants.LOGO_WIDTH / Math.max(1, reduceBy);
        double h = Constants.LOGO_HEIGHT / Math.max(1, reduceBy);

        int spacing = 10;

        Gui.drawModalRectWithCustomSizedTexture((int) ((sr.getScaledWidth() - w) / 2), (int) ((sr.getScaledHeight() / 2 + spacing) - h), 0, 0, (int) w, (int) h, (int) w,(int) h);

        int barHeight = 20;
        double newCurrentProgress = progress.getCurrent();
        double progressDouble = (newCurrentProgress / progress.getMax());
        progressDouble *= sr.getScaledWidth() / 2;
        Gui.drawRect(sr.getScaledWidth() / 4, sr.getScaledHeight() / 2 + spacing, sr.getScaledWidth() / 4 * 3, sr.getScaledHeight() / 2 + spacing + barHeight, new Color(0, 0, 0, 100).getRGB());
        Gui.drawRect(sr.getScaledWidth() / 4, sr.getScaledHeight() / 2 + spacing, (int) (sr.getScaledWidth() / 4 + progressDouble), sr.getScaledHeight() / 2 + spacing + barHeight, new Color(201, 57, 53, 200).getRGB());

        framebuffer.unbindFramebuffer();
        framebuffer.framebufferRender(sr.getScaledWidth() * i, sr.getScaledHeight() * i);
        GlStateManager.enableAlpha();
        GlStateManager.alphaFunc(516, 0.1F);

        Minecraft.getMinecraft().updateDisplay();
    }

}
