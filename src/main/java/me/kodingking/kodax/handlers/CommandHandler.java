package me.kodingking.kodax.handlers;

import com.darkmagician6.eventapi.EventTarget;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import me.kodingking.kodax.Kodax;
import me.kodingking.kodax.command.Command;
import me.kodingking.kodax.command.impl.SettingsPresetCommand;
import me.kodingking.kodax.events.player.ChatMessageSendEvent;
import me.kodingking.kodax.mods.autogg.command.AutoGGCommand;
import me.kodingking.kodax.mods.autotip.command.AutoTipCommand;
import me.kodingking.kodax.mods.keystrokes.command.KeystrokesCommand;
import net.minecraft.client.Minecraft;

public class CommandHandler {

  @Retention(RetentionPolicy.RUNTIME)
  public @interface CommandRegister {

  }

  private List<Command> commands = new ArrayList<>();

  @CommandRegister
  private final KeystrokesCommand keystrokesCommand = new KeystrokesCommand();
  @CommandRegister
  private final AutoGGCommand autoGGCommand = new AutoGGCommand();
  @CommandRegister
  private final AutoTipCommand autoTipCommand = new AutoTipCommand();
  @CommandRegister
  private final SettingsPresetCommand settingsPresetCommand = new SettingsPresetCommand();

  public void loadCommands() {
    Kodax.EVENT_BUS.register(this);
    for (Field f : getClass().getDeclaredFields()) {
      if (f.getDeclaredAnnotation(CommandRegister.class) != null) {
        try {
          f.setAccessible(true);
          commands.add((Command) f.get(this));
        } catch (IllegalAccessException e) {
          e.printStackTrace();
        }
      }
    }
  }

  public void registerCommand(Command command) {
    commands.add(command);
  }

  public List<Command> getCommands() {
    return commands;
  }

  @EventTarget
  public void onChatMessageSend(ChatMessageSendEvent e) {
    String message = e.getMessage();
    if (message.isEmpty() || !message.startsWith("/")) {
      return;
    }
    String command = e.getMessage().substring(1).split(" ")[0];
    String[] args = e.getMessage().substring(command.length() + 1).trim().isEmpty() ? new String[0]
        : e.getMessage().substring(command.length() + 1).trim().split(" ");
    for (Command c : commands) {
      if (c.getName().equalsIgnoreCase(command)) {
        Minecraft.getMinecraft().displayGuiScreen(null);
        e.setCancelled(true);

        c.onCommand(args);
        return;
      }
    }
  }

}
