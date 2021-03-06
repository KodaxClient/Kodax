package me.kodingking.kodax.event.events.gui;

import me.kodingking.kodax.event.Event;

public class GuiScreenDrawEvent extends Event {

    public enum Type {
        PRE,
        POST
    }

    private Type type;
    private int mouseX, mouseY;
    private float partialTicks;

    public GuiScreenDrawEvent(Type type, int mouseX, int mouseY, float partialTicks) {
        this.type = type;
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        this.partialTicks = partialTicks;
    }

    public Type getType() {
        return type;
    }

    public int getMouseX() {
        return mouseX;
    }

    public int getMouseY() {
        return mouseY;
    }

    public float getPartialTicks() {
        return partialTicks;
    }
}
