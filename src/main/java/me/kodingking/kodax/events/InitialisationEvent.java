package me.kodingking.kodax.events;

import com.darkmagician6.eventapi.events.Event;

public class InitialisationEvent implements Event {

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
