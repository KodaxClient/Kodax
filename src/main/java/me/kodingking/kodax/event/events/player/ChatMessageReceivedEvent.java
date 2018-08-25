package me.kodingking.kodax.event.events.player;

import me.kodingking.kodax.event.Event;

public class ChatMessageReceivedEvent extends Event {

    private String message;

    public ChatMessageReceivedEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
