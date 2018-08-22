package me.kodingking.kodax.gui.main;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import me.kodingking.kodax.gui.main.elements.GuiElement;
import net.minecraft.client.gui.Gui;

public class SidebarElement {

  private String name;
  private List<GuiElement> elementList;

  public SidebarElement(String name, List<GuiElement> elementList) {
    this.name = name;
    if (elementList != null) {
      this.elementList = elementList;
    } else {
      this.elementList = new ArrayList<>();
    }
  }

  private int elementOffset = 0;
  private double maxAmountToRender = 0;

  public void addElement(GuiElement element) {
    elementList.add(element);
  }

  void scroll(int i) {
    if ((elementOffset == 0 && i == -1) || (elementOffset == elementList.size() && i == 1)) {
      return;
    }
    if (i == 1) {
      elementOffset++;
    } else if (i == -1) {
      elementOffset--;
    }
  }

  void renderScreen(int mouseX, int mouseY, int x, int y, int width, int height, int colorOffset,
      int fullColorOffset) {
    int yCount = y;

    double amountToRender = Math.min(elementList.size(),
        Math.floor(elementList.size() * (height / GuiElement.elementHeight)));
    if (maxAmountToRender == 0) {
      maxAmountToRender = Math.min(elementList.size(),
          Math.floor(elementList.size() * (height / GuiElement.elementHeight)));
    }

    for (int i = elementOffset; i < amountToRender; i++) {
      if (mouseX >= x &&
          mouseY <= yCount + GuiElement.elementHeight &&
          mouseY > yCount) {
        Gui.drawRect(x, y + yCount, x + width, y + yCount + GuiElement.elementHeight,
            new Color(170, 170, 170, 150 - colorOffset).getRGB());
      }

      GuiElement e = elementList.get(i);

      e.render(mouseX, mouseY, x, y + yCount, width, GuiElement.elementHeight, fullColorOffset);

      yCount += GuiElement.elementHeight;
    }
  }

  public String getName() {
    return name;
  }

  public List<GuiElement> getElementList() {
    return elementList;
  }
}
