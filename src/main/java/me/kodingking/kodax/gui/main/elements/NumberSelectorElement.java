package me.kodingking.kodax.gui.main.elements;

import me.kodingking.kodax.gui.main.KodaxGuiScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

public class NumberSelectorElement extends GuiElement {

  private String label;
  private double min, current, max, increment;
  private Callback callback;

  public NumberSelectorElement(String label, double min, double current, double max,
      double increment, Callback callback) {
    this.label = label;
    this.min = min;
    this.current = current;
    this.max = max;
    this.increment = increment;
    this.callback = callback;
  }

  @Override
  public void render(int mouseX, int mouseY, int x, int y, int width, int height,
      int fullColorOffset) {
    FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;

    KodaxGuiScreen.drawString(label, 5 + x, y + (height / 2 - fr.FONT_HEIGHT / 3), false);
    KodaxGuiScreen
        .drawString(String.valueOf(current), x + width - fr.getStringWidth(String.valueOf(current)),
            y + (height / 2 - fr.FONT_HEIGHT / 3), false);

    super.render(mouseX, mouseY, x, y, width, height, fullColorOffset);
  }

  @Override
  public void mouseClicked(int mouseX, int mouseY, int mouseButton, int x, int y, int width,
      int height) {

    if (mouseX > x &&
        mouseX <= x + width &&
        mouseY > y &&
        mouseY <= y + height) {
      double newCurrent = current;

      switch (mouseButton) {
        case 0:
          newCurrent += increment;
          break;
        case 1:
          newCurrent -= increment;
          break;
      }

      if (newCurrent > max || newCurrent < min) {
        return;
      }

      current = newCurrent;
      callback.run(current);
    }

    super.mouseClicked(mouseX, mouseY, mouseButton, x, y, width, height);
  }

  public String getLabel() {
    return label;
  }

  public double getMin() {
    return min;
  }

  public double getCurrent() {
    return current;
  }

  public double getMax() {
    return max;
  }

  public double getIncrement() {
    return increment;
  }

  public interface Callback {

    void run(double current);
  }
}
