package me.kodingking.kodax.event.events;

import me.kodingking.kodax.event.Event;

public class MouseClickEvent extends Event {

    public enum Type {
        PRE,
        POST
    }

    private int mouseButton, mouseX, mouseY;
    private Type type;

    public MouseClickEvent(int mouseButton, int mouseX, int mouseY, Type type) {
        this.mouseButton = mouseButton;
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        this.type = type;
    }

    public int getMouseButton() {
        return mouseButton;
    }

    public int getMouseX() {
        return mouseX;
    }

    public int getMouseY() {
        return mouseY;
    }

    public Type getType() {
        return type;
    }
}
