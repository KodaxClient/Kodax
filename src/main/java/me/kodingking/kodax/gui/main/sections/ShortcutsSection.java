package me.kodingking.kodax.gui.main.sections;

import me.kodingking.kodax.Kodax;
import me.kodingking.kodax.gui.main.SidebarElement;
import me.kodingking.kodax.gui.main.elements.ButtonElement;
import me.kodingking.kodax.mods.keystrokes.gui.ConfigGui;
import net.minecraft.client.Minecraft;

public class ShortcutsSection extends SidebarElement {

  public ShortcutsSection() {
    super("Shortcuts", null);

    addElement(new ButtonElement("Keystrokes Config", () -> Minecraft.getMinecraft()
        .displayGuiScreen(new ConfigGui(
            Kodax.INSTANCE.getHandlerController().getModHandler().getKeystrokesMod()
                .getConfig()))));
  }
}
