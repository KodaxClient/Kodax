package me.kodingking.kodax.event.events.player;

import me.kodingking.kodax.event.CancellableEvent;

public class ChatMessageSendEvent extends CancellableEvent {

    private String message;

    public ChatMessageSendEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
