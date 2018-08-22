package me.kodingking.kodax.events.player;

import com.darkmagician6.eventapi.events.callables.EventCancellable;

public class ChatMessageSendEvent extends EventCancellable {

    private String message;

    public ChatMessageSendEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
