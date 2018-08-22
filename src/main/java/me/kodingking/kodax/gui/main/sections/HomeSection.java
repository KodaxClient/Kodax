package me.kodingking.kodax.gui.main.sections;

import me.kodingking.kodax.gui.main.SidebarElement;
import me.kodingking.kodax.gui.main.elements.GuiElement;
import me.kodingking.kodax.gui.main.elements.LabelElement;

public class HomeSection extends SidebarElement {

  public HomeSection() {
    super("Home", null);
    addElement(new LabelElement("Kodax"));
    addElement(new LabelElement(GuiElement.spacerText));
  }

}
