package me.kodingking.kodax.gui.main.elements;

import org.lwjgl.input.Mouse;

import java.util.HashMap;
import java.util.Map;

public abstract class GuiElement {

    public static final int elementHeight = 20;
    public static final String spacerText = "--------------";

    private Map<Integer, Boolean> mouseMap;

    public void render(int mouseX, int mouseY, int x, int y, int width, int height, int fullColorOffset) {
        if (mouseMap == null)
            mouseMap = new HashMap<>();

        for (int i = 0; i <= 2; i++) {
            boolean isButtonDown = Mouse.isButtonDown(i);

            mouseMap.putIfAbsent(i, false);
            boolean mouse = mouseMap.get(i);

            if (!mouse && isButtonDown) {
                mouseClicked(mouseX, mouseY, i, x, y, width, height);
                mouseMap.replace(i, true);
            } else if (!isButtonDown && mouse) {
                mouseMap.replace(i, false);
            }
        }
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton, int x, int y, int width, int height) {

    }

    public void updateScreen() {

    }

}
