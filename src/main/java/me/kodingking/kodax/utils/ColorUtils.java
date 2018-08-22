package me.kodingking.kodax.utils;

import java.awt.*;

public class ColorUtils {

    public static Color getChromaColor(int offset, float saturation, float brightness) {
        double rainbowState = Math.ceil((System.currentTimeMillis() + offset) / 10);
        rainbowState %= 360;
        return Color.getHSBColor((float) (rainbowState / 360.0F), saturation, brightness);
    }

}
