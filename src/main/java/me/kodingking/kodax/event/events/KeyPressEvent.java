package me.kodingking.kodax.event.events;

import me.kodingking.kodax.event.Event;

public class KeyPressEvent extends Event {

    private int key;

    public KeyPressEvent(int key) {
        this.key = key;
    }

    public int getKey() {
        return key;
    }
}
