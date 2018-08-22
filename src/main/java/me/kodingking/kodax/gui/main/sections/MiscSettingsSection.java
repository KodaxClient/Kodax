package me.kodingking.kodax.gui.main.sections;

import me.kodingking.kodax.GlobalSettings;
import me.kodingking.kodax.Kodax;
import me.kodingking.kodax.gui.main.SidebarElement;
import me.kodingking.kodax.gui.main.elements.ToggleElement;
import me.kodingking.kodax.utils.GuiUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class MiscSettingsSection extends SidebarElement {

  public MiscSettingsSection() {
    super("Misc Settings", null);

    GlobalSettings settings = Kodax.INSTANCE.getSettings();

    addElement(new ToggleElement("Settings GUI Chroma Text", settings.SETTINGS_GUI_CHROMA_TEXT,
        current -> settings.SETTINGS_GUI_CHROMA_TEXT = current));
    addElement(new ToggleElement("Invert Scrolling", settings.INVERT_SCROLLING,
        current -> settings.INVERT_SCROLLING = current));
    addElement(new ToggleElement("Discord RPC (Requires restart)", settings.DISCORD_RPC,
        current -> settings.DISCORD_RPC = current));
    addElement(
        new ToggleElement("Blurred GUI Background", settings.BLURRED_GUI_BACKGROUND, current -> {
          settings.BLURRED_GUI_BACKGROUND = current;
          if (Minecraft.getMinecraft().theWorld != null) {
            if (current) {
              GuiUtils
                  .applyShader(
                      new ResourceLocation("minecraft", "shaders/post/fade_in_blur.json"));
            } else {
              GuiUtils.unloadShader();
            }
          }
        }));
  }

}
