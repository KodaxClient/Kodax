package me.kodingking.kodax.mods.autotip.command;

import me.kodingking.kodax.Kodax;
import me.kodingking.kodax.command.Command;
import me.kodingking.kodax.mods.autotip.config.AutoTipConfig;
import me.kodingking.kodax.utils.ChatColor;

public class AutoTipCommand extends Command {

  @Override
  public String getName() {
    return "autotip";
  }

  @Override
  public String getUsage() {
    return "/autotip <Toggle|Interval [New Interval]>";
  }

  @Override
  public void onCommand(String[] args) {
    AutoTipConfig config = Kodax.INSTANCE.getHandlerController().getModHandler().getAutoTipMod()
        .getConfig();
    if (args.length <= 0) {
      sendMsg(ChatColor.RED + getUsage());
    } else if (args[0].equalsIgnoreCase("toggle")) {
      config.ENABLED = !config.ENABLED;
      sendMsg((config.ENABLED ? "Enabled" : "Disabled") + " Auto Tip.");
    } else if (args[0].equalsIgnoreCase("interval")) {
      if (args.length <= 1) {
        sendMsg("The current interval is " + config.INTERVAL + " minutes.");
      } else {
        try {
          int integer = Integer.parseInt(args[1]);
          if (integer < 1 || integer > 200) {
            sendMsg(ChatColor.RED + "Please enter a number 1 - 200");
          } else {
            config.INTERVAL = integer;
            sendMsg("Set the interval to " + integer + " minutes.");
          }
        } catch (NumberFormatException e) {
          sendMsg(ChatColor.RED + "Please enter a valid number.");
        }
      }
    } else {
      sendMsg(ChatColor.RED + "Unrecognised sub-command.");
      sendMsg(ChatColor.RED + getUsage());
    }
  }

}
