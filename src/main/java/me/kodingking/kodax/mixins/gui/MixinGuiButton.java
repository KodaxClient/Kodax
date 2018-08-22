package me.kodingking.kodax.mixins.gui;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;

@Mixin(GuiButton.class)
public abstract class MixinGuiButton extends Gui {

  @Shadow
  public boolean visible;
  @Shadow
  protected boolean hovered;
  @Shadow
  public int xPosition;
  @Shadow
  public int yPosition;
  @Shadow
  protected int width;
  @Shadow
  protected int height;

  @Shadow
  protected abstract void mouseDragged(Minecraft mc, int mouseX, int mouseY);

  @Shadow
  public String displayString;

  private double hoverFade = 0;

  private long prevDeltaTime;

  /**
   * @author KodingKing
   */
  @Overwrite
  public void drawButton(Minecraft mc, int mouseX, int mouseY) {
    if (prevDeltaTime == 0)
      prevDeltaTime = System.currentTimeMillis();
    if (this.visible) {
      FontRenderer fontrenderer = mc.fontRendererObj;
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      this.hovered =
          mouseX >= this.xPosition && mouseY >= this.yPosition
              && mouseX < this.xPosition + this.width
              && mouseY < this.yPosition + this.height;

      double hoverInc = (System.currentTimeMillis() - prevDeltaTime) / 2;
      if (this.hovered && hoverFade + hoverInc <= 100) {
        hoverFade += hoverInc;
      } else if (!this.hovered && hoverFade - hoverInc >= 0) {
        hoverFade -= hoverInc;
      }

      Gui.drawRect((int) (xPosition + (hoverFade / 10)), yPosition,
          (int) (xPosition + width - (hoverFade / 10)), yPosition + height, new Color(0, 0, 0,
              (int) (175 - hoverFade)).getRGB());

      this.mouseDragged(mc, mouseX, mouseY);

      int colorChange = (int) (155 + hoverFade);

      drawCenteredString(fontrenderer, this.displayString, this.xPosition + this.width / 2,
          this.yPosition + (this.height - 8) / 2, new Color(colorChange, colorChange, colorChange, 255).getRGB());

      prevDeltaTime = System.currentTimeMillis();
    }
  }

}
