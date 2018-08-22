package me.kodingking.kodax.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.EnumChatFormatting;

public class FontUtils {

    public static void drawChromaString(int x, int y, String text, boolean dropShadow, EnumChatFormatting chatFormatting) {
        drawChromaString(x, y, text, dropShadow, 1.0, chatFormatting);
    }

    public static void drawChromaString(int x, int y, String text, boolean dropShadow, double glOffset) {
        drawChromaString(x, y, text, dropShadow, glOffset, null);
    }

    public static void drawChromaString(int x, int y, String text, boolean dropShadow) {
        drawChromaString(x, y, text, dropShadow, 1.0, null);
    }

    public static void drawChromaString(int x, int y, String text, boolean dropShadow, double glOffset, EnumChatFormatting chatFormatting) {
        FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;
        int total = 0;
        for (char c : text.toCharArray()) {
            fr.drawString(String.valueOf((chatFormatting != null ? chatFormatting.toString() : "") + c), x + total, y, ColorUtils.getChromaColor(
                (int) ((((x + total) - y) * 22) * glOffset), 0.8F, 1F).getRGB(), dropShadow);
            total += fr.getCharWidth(c);
        }
    }

}
