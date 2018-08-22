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
    sb.append(message);
    Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(sb.toString()));
  }

  public static void addChatMessage(String message) {
    addChatMessage(message, true);
  }

  public static void sendChatMessage(String s) {
    if (Minecraft.getMinecraft().thePlayer == null)
      return;
    Minecraft.getMinecraft().thePlayer.sendChatMessage(s);
  }
}
