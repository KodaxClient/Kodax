package me.kodingking.kodax.events.player;

import com.darkmagician6.eventapi.events.Event;

public class ChatMessageReceivedEvent implements Event {

    private String message;

    public ChatMessageReceivedEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
