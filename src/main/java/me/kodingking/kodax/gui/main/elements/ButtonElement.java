package me.kodingking.kodax.gui.main.elements;

import me.kodingking.kodax.gui.main.KodaxGuiScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

public class ButtonElement extends GuiElement {

  private String label;
  private Runnable clickRunnable;

  public ButtonElement(String label, Runnable runnable) {
    this.label = label;
    this.clickRunnable = runnable;
  }

  @Override
  public void render(int mouseX, int mouseY, int x, int y, int width, int height,
      int fullColorOffset) {
    FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;
    KodaxGuiScreen.drawString(label, 5 + x, y + (elementHeight / 2 - fr.FONT_HEIGHT / 3));

    super.render(mouseX, mouseY, x, y, width, height, fullColorOffset);
  }

  public void mouseClicked(int mouseX, int mouseY, int mouseButton, int x, int y, int width,
      int height) {
    if (mouseX > x &&
        mouseX <= x + width &&
        mouseY > y &&
        mouseY <= y + height) {
      clickRunnable.run();
      Minecraft.getMinecraft().thePlayer.playSound("random.click", 1.0F, 1.0F);
    }
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public Runnable getClickRunnable() {
    return clickRunnable;
  }
}
