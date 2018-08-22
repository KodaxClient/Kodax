package me.kodingking.kodax.utils;

import me.kodingking.kodax.Kodax;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

public class ChatUtils {

  private static String PREFIX =
      ChatColor.DARK_AQUA + "[" + ChatColor.AQUA + ChatColor.BOLD + Kodax.INSTANCE.getClientName()
          + ChatColor.DARK_AQUA + "] " + ChatColor.WHITE;

  public static void addChatMessage(String message, boolean prefix) {
    StringBuilder sb = new StringBuilder();
    if (prefix) {
      sb.append(PREFIX);
    }

    String currentColor = "";

    for (int i = 0; i < message.length(); i++) {
      char currentChar = message.charAt(i);

      if (currentChar == '§') {
        if (currentColor.isEmpty())
          sb.append("§").append(message.charAt(i + 1));
        currentColor = "§" + message.charAt(i + 1);
        i++;
        if (i >= message.length())
          break;
      } else {
        sb.append(currentColor).append(currentChar);
      }
    }

    Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(sb.toString()));
  }

  public static void addChatMessage(String message) {
    addChatMessage(message, true);
  }

  public static void sendChatMessage(String s) {
    if (Minecraft.getMinecraft().thePlayer == null)
      return;
    addChatMessage(s, false);
  }
}
