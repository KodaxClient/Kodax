package me.kodingking.kodax.mods.keystrokes.command;

import me.kodingking.kodax.Kodax;
import me.kodingking.kodax.command.Command;
import me.kodingking.kodax.mods.keystrokes.gui.ConfigGui;
import net.minecraft.client.Minecraft;

public class KeystrokesCommand extends Command {

  @Override
  public String getName() {
    return "keystrokes";
  }

  @Override
  public String getUsage() {
    return "/keystrokes";
  }

  @Override
  public void onCommand(String[] args) {
    sendMsg("Opened keystrokes menu...");
    Kodax.SCHEDULER.schedule(() -> Minecraft.getMinecraft().displayGuiScreen(new ConfigGui(
        Kodax.INSTANCE.getHandlerController().getModHandler().getKeystrokesMod().getConfig())), 2);
  }
}
