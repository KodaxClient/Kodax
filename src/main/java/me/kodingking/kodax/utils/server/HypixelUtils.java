package me.kodingking.kodax.utils.server;

import net.minecraft.client.Minecraft;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class HypixelUtils {

    private static List<String> triggers = Arrays.asList(
            "1st Killer -",
            "1st Place -",
            "Winner:",
            "- Damage Dealt -",
            "Winning Team -",
            "1st -",
            "Winners:",
            "Winner:",
            "Winning Team:",
            "won the game!",
            "Top Seeker:",
            "1st Place:",
            "Last team standing!",
            "Winner #1 (",
            "Top Survivors",
            "Winners -",
            "Winner -",
            "WINNER!"
    );

    private static List<String> replaceTriggers = Collections.singletonList(
            "%s WINNER!"
    );

    public static boolean hasWonGame(String message) {
        if (!message.contains(Minecraft.getMinecraft().getSession().getUsername()))
            return false;
        for (String trigger : triggers) {
            if (message.contains(trigger))
                return true;
        }
        for (String trigger : replaceTriggers) {
            if (message.contains(String.format(trigger, Minecraft.getMinecraft().getSession().getUsername())))
                return true;
        }
        return message.contains("+") && message.contains("coins") && message.contains("Win");
    }

    public static boolean hasGameEnded(String message) {
        for (String trigger : triggers) {
            if (message.contains(trigger))
                return true;
        }
        return false;
    }

    public static boolean isConnectedToHypixel() {
        return Minecraft.getMinecraft().getCurrentServerData() != null && Minecraft.getMinecraft().getCurrentServerData().serverIP.toLowerCase().contains(".hypixel.net");
    }

}
