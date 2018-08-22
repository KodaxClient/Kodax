package me.kodingking.kodax.gui.main.elements;

import java.awt.Color;
import me.kodingking.kodax.gui.main.KodaxGuiScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;

public class ToggleElement extends GuiElement {

  private String label;
  private boolean toggled;
  private Callback callback;

  private double fadeVal = 50;

  public ToggleElement(String label, boolean toggled, Callback callback) {
    this.label = label;
    this.toggled = toggled;
    this.callback = callback;
  }

  @Override
  public void render(int mouseX, int mouseY, int x, int y, int width, int height,
      int fullColorOffset) {
    FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;

    KodaxGuiScreen.drawString(label, 5 + x, y + (height / 2 - fr.FONT_HEIGHT / 3));

    Color color = Color.getHSBColor((float) ((fadeVal / 255)), 0.8F, 0.8F);
    int colorInt = new Color(color.getRed(), color.getGreen(), color.getBlue(),
        Math.max(1, 255 - fullColorOffset)).getRGB();

    Gui.drawRect(x + width - 40, y, x + width, y + height, colorInt);

    String text = toggled ? "YES" : "NO";
    fr.drawString(text, x + width - 20 - fr.getStringWidth(text) / 2,
        y + (height / 2 - fr.FONT_HEIGHT / 3), Color.WHITE.getRGB(), false);

    super.render(mouseX, mouseY, x, y, width, height, fullColorOffset);
  }

  @Override
  public void updateScreen() {
    if (toggled && fadeVal + 5 < 90) {
      fadeVal += 5;
    } else if (!toggled && fadeVal - 5 > 0) {
      fadeVal -= 5;
    }
  }

  @Override
  public void mouseClicked(int mouseX, int mouseY, int mouseButton, int x, int y, int width,
      int height) {
    if (mouseX > x &&
        mouseX <= x + width &&
        mouseY > y &&
        mouseY <= y + height) {

      toggled = !toggled;
      callback.run(toggled);
      Minecraft.getMinecraft().thePlayer.playSound("random.click", 1.0F, 1.0F);
    }

    super.mouseClicked(mouseX, mouseY, mouseButton, x, y, width, height);
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public String getLabel() {
    return label;
  }

  public boolean isToggled() {
    return toggled;
  }

  public Callback getCallback() {
    return callback;
  }

  public interface Callback {

    void run(boolean current);
  }
}
