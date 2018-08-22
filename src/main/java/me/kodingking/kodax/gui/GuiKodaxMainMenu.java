package me.kodingking.kodax.gui;

import me.kodingking.kodax.Constants;
import me.kodingking.kodax.Kodax;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiLabel;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import me.kodingking.kodax.Kodax;

public class GuiKodaxMainMenu extends GuiMainMenu {

  private ResourceLocation backgroundLocation = new ResourceLocation(
      "textures/splash_background.png");
  private ResourceLocation logoLocation = new ResourceLocation("textures/logo.png");

  @Override
  public void drawScreen(int mouseX, int mouseY, float partialTicks) {
    ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
    GlStateManager.disableAlpha();
    GlStateManager.enableAlpha();
    int i = 274;

    GL11.glEnable(GL11.GL_BLEND);
    GL11.glDisable(GL11.GL_DEPTH_TEST);
    GL11.glDepthMask(false);
    GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    GL11.glDisable(GL11.GL_ALPHA_TEST);

    this.mc.getTextureManager().bindTexture(backgroundLocation);
    Gui.drawModalRectWithCustomSizedTexture(0, 0, 0, 0, sr.getScaledWidth(), sr.getScaledHeight(),
        sr.getScaledWidth(), sr.getScaledHeight());

    this.mc.getTextureManager().bindTexture(logoLocation);
    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

    double reduceBy = 4;

    double w = Constants.LOGO_WIDTH / Math.max(1, reduceBy);
    double h = Constants.LOGO_HEIGHT / Math.max(1, reduceBy);

    Gui.drawModalRectWithCustomSizedTexture((int) ((sr.getScaledWidth() - w) / 2),
        (int) ((this.height / 4 + 48) - h), 0, 0, (int) w, (int) h, (int) w, (int) h);

    String s = Kodax.INSTANCE.getClientName() + " 1.8.9";

    if (this.mc.isDemo()) {
      s = s + " Demo";
    }

    this.drawString(this.fontRendererObj, s, 2, this.height - 10, -1);
    String s1 = "Copyright Mojang AB. Do not distribute!";
    this.drawString(this.fontRendererObj, s1,
        this.width - this.fontRendererObj.getStringWidth(s1) - 2, this.height - 10, -1);

    for (int i1 = 0; i1 < this.buttonList.size(); ++i1) {
      ((GuiButton) this.buttonList.get(i1)).drawButton(this.mc, mouseX, mouseY);
    }

    for (int j1 = 0; j1 < this.labelList.size(); ++j1) {
      ((GuiLabel) this.labelList.get(j1)).drawLabel(this.mc, mouseX, mouseY);
    }
  }
}
