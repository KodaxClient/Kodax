package me.kodingking.kodax.events;

import com.darkmagician6.eventapi.events.callables.EventCancellable;

public class KeyPressEvent extends EventCancellable {

    private int key;

    public KeyPressEvent(int key) {
        this.key = key;
    }

    public int getKey() {
        return key;
    }
}
