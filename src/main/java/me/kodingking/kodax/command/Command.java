package me.kodingking.kodax.command;

import me.kodingking.kodax.utils.ChatUtils;

public abstract class Command {

  public abstract String getName();

  public abstract String getUsage();

  public abstract void onCommand(String[] args);

  public void sendMsg(String message) {
    ChatUtils.addChatMessage(message, true);
  }

}
