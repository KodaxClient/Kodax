package me.kodingking.kodax.gui.main.sections;

import me.kodingking.kodax.GlobalSettings;
import me.kodingking.kodax.Kodax;
import me.kodingking.kodax.gui.main.SidebarElement;
import me.kodingking.kodax.gui.main.elements.ButtonElement;
import me.kodingking.kodax.gui.main.elements.GuiElement;
import me.kodingking.kodax.gui.main.elements.LabelElement;
import me.kodingking.kodax.gui.main.elements.NumberSelectorElement;
import me.kodingking.kodax.gui.main.elements.SelectorElement;
import me.kodingking.kodax.gui.main.elements.ToggleElement;
import me.kodingking.kodax.mods.autogg.config.AutoGGConfig;
import me.kodingking.kodax.mods.autotip.config.AutoTipConfig;
import me.kodingking.kodax.mods.keystrokes.KeystrokesConfig;
import me.kodingking.kodax.mods.keystrokes.gui.ConfigGui;
import me.kodingking.kodax.mods.togglesprint.config.ToggleSprintConfig;
import net.minecraft.client.Minecraft;

public class ModSettingsSection extends SidebarElement {

  public ModSettingsSection() {
    super("Mod Settings", null);

    KeystrokesConfig keystrokesConfig = Kodax.INSTANCE.getHandlerController().getModHandler()
        .getKeystrokesMod().getConfig();

    addElement(new LabelElement("Keystrokes"));
    addElement(new LabelElement(GuiElement.spacerText));
    addElement(new ToggleElement("Enabled", keystrokesConfig.ENABLED,
        current -> keystrokesConfig.ENABLED = current));
    addElement(new NumberSelectorElement("Scale", 75, keystrokesConfig.SCALE, 200, 25,
        current -> keystrokesConfig.SCALE = (int) current));
    addElement(new NumberSelectorElement("Fade increment", 1, keystrokesConfig.FADE_INCREMENT, 7, 1,
        current -> keystrokesConfig.FADE_INCREMENT = (int) current));
    addElement(new NumberSelectorElement("Spacing", 0, keystrokesConfig.SPACING, 10, 1,
        current -> keystrokesConfig.SPACING = (int) current));
    addElement(new SelectorElement("Corner", keystrokesConfig.getCorner(), new String[]{
        "Top Left",
        "Top Right",
        "Bottom Left",
        "Bottom Right"
    }, current -> {
      switch (current) {
        case "Top Left":
          keystrokesConfig.CORNER = 1;
          break;
        case "Top Right":
          keystrokesConfig.CORNER = 2;
          break;
        case "Bottom Left":
          keystrokesConfig.CORNER = 3;
          break;
        case "Bottom Right":
          keystrokesConfig.CORNER = 4;
          break;
      }
    }));
    addElement(new SelectorElement("Mode", keystrokesConfig.MODE, new String[]{
        "CHROMA",
        "RGB"
    }, current -> keystrokesConfig.MODE = current));
    addElement(new ToggleElement("Drop shadow", keystrokesConfig.ENABLED,
        current -> keystrokesConfig.DROP_SHADOW = current));
    addElement(new ToggleElement("Display space", keystrokesConfig.DISPLAY_SPACE,
        current -> keystrokesConfig.DISPLAY_SPACE = current));
    addElement(new ToggleElement("Display mouse buttons", keystrokesConfig.DISPLAY_MOUSE_BUTTONS,
        current -> keystrokesConfig.DISPLAY_MOUSE_BUTTONS = current));
    addElement(new ToggleElement("Display CPS", keystrokesConfig.DISPLAY_CPS,
        current -> keystrokesConfig.DISPLAY_CPS = current));
    addElement(new ButtonElement("Click for more settings",
        () -> Minecraft.getMinecraft().displayGuiScreen(new ConfigGui(keystrokesConfig))));

    AutoGGConfig autoGGConfig = Kodax.INSTANCE.getHandlerController().getModHandler()
        .getAutoGGMod().getConfig();

    addElement(new LabelElement(GuiElement.spacerText));
    addElement(new LabelElement("AutoGG"));
    addElement(new LabelElement(GuiElement.spacerText));
    addElement(new ToggleElement("Enabled", autoGGConfig.ENABLED,
        current -> autoGGConfig.ENABLED = current));
    addElement(new NumberSelectorElement("Delay", 0, autoGGConfig.DELAY, 10, 1,
        current -> autoGGConfig.DELAY = (int) current));

    AutoTipConfig autoTipConfig = Kodax.INSTANCE.getHandlerController().getModHandler()
        .getAutoTipMod().getConfig();

    addElement(new LabelElement(GuiElement.spacerText));
    addElement(new LabelElement("Auto Tip"));
    addElement(new LabelElement(GuiElement.spacerText));
    addElement(new ToggleElement("Enabled", autoTipConfig.ENABLED,
        current -> autoTipConfig.ENABLED = current));
    addElement(new NumberSelectorElement("Interval", 5, autoTipConfig.INTERVAL, 120, 15,
        current -> autoTipConfig.INTERVAL = (int) current));

    ToggleSprintConfig toggleSprintConfig = Kodax.INSTANCE.getHandlerController().getModHandler()
        .getToggleSprintMod().getConfig();

    addElement(new LabelElement(GuiElement.spacerText));
    addElement(new LabelElement("Toggle Sprint"));
    addElement(new LabelElement(GuiElement.spacerText));
    addElement(new ToggleElement("Display status", toggleSprintConfig.DISPLAY_STATUS,
        current -> toggleSprintConfig.DISPLAY_STATUS = current));
    addElement(new ToggleElement("Chroma display", toggleSprintConfig.CHROMA_DISPLAY,
        current -> toggleSprintConfig.CHROMA_DISPLAY = current));
    addElement(new ToggleElement("Display drop shadow", toggleSprintConfig.DISPLAY_DROP_SHADOW,
        current -> toggleSprintConfig.DISPLAY_DROP_SHADOW = current));

    GlobalSettings globalSettings = Kodax.INSTANCE.getSettings();

    addElement(new LabelElement(GuiElement.spacerText));
    addElement(new LabelElement("Other Mods"));
    addElement(new LabelElement(GuiElement.spacerText));
    addElement(new ToggleElement("Fast Chat", globalSettings.FAST_CHAT,
        current -> globalSettings.FAST_CHAT = current));
  }
}
