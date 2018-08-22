package me.kodingking.kodax.gui.main.elements;

import me.kodingking.kodax.gui.main.KodaxGuiScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

public class LabelElement extends GuiElement {

  private String text;

  public LabelElement(String text) {
    this.text = text;
  }

  @Override
  public void render(int mouseX, int mouseY, int x, int y, int width, int height,
      int fullColorOffset) {
    FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;

    KodaxGuiScreen.drawString(text, 5 + x, y + (elementHeight / 2 - fr.FONT_HEIGHT / 3));

    super.render(mouseX, mouseY, x, y, width, height, fullColorOffset);
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }
}
