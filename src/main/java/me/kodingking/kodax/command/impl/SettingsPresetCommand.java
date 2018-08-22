package me.kodingking.kodax.command.impl;

import me.kodingking.kodax.command.Command;
import me.kodingking.kodax.utils.ChatUtils;
import me.kodingking.kodax.utils.SettingsPresetUtil;

public class SettingsPresetCommand extends Command {

  private String confirmationMessage = "YesIAmSure";

  @Override
  public String getName() {
    return "settingspreset";
  }

  @Override
  public String getUsage() {
    return "settingspreset <Koding> " + confirmationMessage;
  }

  @Override
  public void onCommand(String[] args) {
    if (args.length <= 1) {
      ChatUtils.addChatMessage("Usage: " + getUsage());
    } else {
      if (!args[1].equals(confirmationMessage)) {
        ChatUtils.addChatMessage(
            "Please re-run the command correctly typing " + confirmationMessage + ".");
      } else {
        if (args[0].equalsIgnoreCase("Koding")) {
          ChatUtils.addChatMessage("Applying Koding preset...");
          SettingsPresetUtil.applyKodingPreset();
          ChatUtils.addChatMessage("Done!");
        } else {
          ChatUtils.addChatMessage("Unrecognized preset.");
        }
      }
    }
  }
}
