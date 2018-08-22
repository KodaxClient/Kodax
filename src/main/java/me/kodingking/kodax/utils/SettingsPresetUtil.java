package me.kodingking.kodax.utils;

import me.kodingking.kodax.GlobalSettings;
import me.kodingking.kodax.Kodax;
import me.kodingking.kodax.mods.autogg.config.AutoGGConfig;
import me.kodingking.kodax.mods.autotip.config.AutoTipConfig;
import me.kodingking.kodax.mods.keystrokes.KeystrokesConfig;
import me.kodingking.kodax.mods.togglesprint.config.ToggleSprintConfig;

public class SettingsPresetUtil {

  public static void applyKodingPreset() {
    GlobalSettings settings = Kodax.INSTANCE.getSettings();
    AutoTipConfig autoTipConfig = Kodax.INSTANCE.getHandlerController().getModHandler().getAutoTipMod().getConfig();
    AutoGGConfig autoGGConfig = Kodax.INSTANCE.getHandlerController().getModHandler().getAutoGGMod().getConfig();
    KeystrokesConfig keystrokesConfig = Kodax.INSTANCE.getHandlerController().getModHandler().getKeystrokesMod().getConfig();
    ToggleSprintConfig toggleSprintConfig = Kodax.INSTANCE.getHandlerController().getModHandler().getToggleSprintMod().getConfig();

    settings.FAST_CHAT = true;
    settings.BLURRED_GUI_BACKGROUND = true;
    settings.DISCORD_RPC = true;
    settings.SETTINGS_GUI_CHROMA_TEXT = true;
    settings.INVERT_SCROLLING = true;

    autoTipConfig.ENABLED = true;
    autoTipConfig.INTERVAL = 10;

    autoGGConfig.ENABLED = true;
    autoGGConfig.DELAY = 0;

    keystrokesConfig.SCALE = 75;
    keystrokesConfig.MODE = "CHROMA";
    keystrokesConfig.DROP_SHADOW = false;
    keystrokesConfig.DISPLAY_CPS = true;
    keystrokesConfig.DISPLAY_MOUSE_BUTTONS = true;
    keystrokesConfig.DISPLAY_SPACE = true;
    keystrokesConfig.CORNER = 1;
    keystrokesConfig.FADE_INCREMENT = 6;
    keystrokesConfig.SPACING = 1;

    toggleSprintConfig.TOGGLED = true;
    toggleSprintConfig.DISPLAY_DROP_SHADOW = false;
    toggleSprintConfig.CHROMA_DISPLAY = true;
    toggleSprintConfig.DISPLAY_STATUS = true;
  }

}
