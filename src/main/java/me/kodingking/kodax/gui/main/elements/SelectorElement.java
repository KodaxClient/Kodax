package me.kodingking.kodax.gui.main.elements;

import java.util.Arrays;
import java.util.List;
import me.kodingking.kodax.gui.main.KodaxGuiScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

public class SelectorElement extends GuiElement {

  private String label, selected;
  private String[] elements;
  private Callback callback;

  public SelectorElement(String label, String selected, String[] elements, Callback callback) {
    this.label = label;
    this.selected = selected;
    this.elements = elements;
    this.callback = callback;
  }

  @Override
  public void render(int mouseX, int mouseY, int x, int y, int width, int height,
      int fullColorOffset) {
    FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;

    KodaxGuiScreen.drawString(label, 5 + x, y + (height / 2 - fr.FONT_HEIGHT / 3));

    KodaxGuiScreen
        .drawString(selected, x + width - 5 - fr.getStringWidth(selected),
            y + (height / 2 - fr.FONT_HEIGHT / 3));

    super.render(mouseX, mouseY, x, y, width, height, fullColorOffset);
  }

  @Override
  public void mouseClicked(int mouseX, int mouseY, int mouseButton, int x, int y, int width,
      int height) {
    if (mouseX > x &&
        mouseX <= x + width &&
        mouseY > y &&
        mouseY <= y + height) {

      List<String> elementsList = Arrays.asList(elements);

      int newIndex = elementsList.indexOf(selected);
      if (newIndex + 1 >= elementsList.size()) {
        newIndex = 0;
      } else {
        newIndex++;
      }

      selected = elementsList.get(newIndex);
      callback.run(selected);

      Minecraft.getMinecraft().thePlayer.playSound("random.click", 1.0F, 1.0F);
    }

    super.mouseClicked(mouseX, mouseY, mouseButton, x, y, width, height);
  }

  public interface Callback {

    void run(String current);
  }
}
