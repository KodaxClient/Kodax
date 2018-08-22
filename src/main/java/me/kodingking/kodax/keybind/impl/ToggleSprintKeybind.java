package me.kodingking.kodax.keybind.impl;

import me.kodingking.kodax.Kodax;
import me.kodingking.kodax.keybind.XenonKeybind;
import me.kodingking.kodax.mods.togglesprint.config.ToggleSprintConfig;
import me.kodingking.kodax.utils.ChatUtils;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

public class ToggleSprintKeybind extends XenonKeybind {

  public ToggleSprintKeybind() {
    super("Toggle Sprint", Keyboard.KEY_V);
  }

  @Override
  public void onPress() {
    ToggleSprintConfig config = Kodax.INSTANCE.getHandlerController().getModHandler()
        .getToggleSprintMod().getConfig();
    config.TOGGLED = !config.TOGGLED;
    ChatUtils.addChatMessage((config.TOGGLED ? "Enabled" : "Disabled") + " toggle-sprint.");
  }

  @Override
  public void onRelease() {
    if (!Kodax.INSTANCE.getHandlerController().getModHandler().getToggleSprintMod()
        .getConfig().TOGGLED && !Minecraft.getMinecraft().gameSettings.keyBindSprint
        .isKeyDown()) {
      Minecraft.getMinecraft().thePlayer.setSprinting(false);
    }
  }
}
