package me.kodingking.kodax.event.events;

import me.kodingking.kodax.event.Event;

public class InitialisationEvent extends Event {

    public enum Type {
        PRE,
        POST
    }

    private Type type;

    public InitialisationEvent(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }
}
