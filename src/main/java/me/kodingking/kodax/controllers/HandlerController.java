package me.kodingking.kodax.controllers;

import me.kodingking.kodax.event.EventBus;
import me.kodingking.kodax.handlers.*;

public class HandlerController {

    private RPCHandler rpcHandler = new RPCHandler();
    private ModHandler modHandler;
    private CommandHandler commandHandler = new CommandHandler();
    private NotificationHandler notificationHandler = new NotificationHandler();
    private KeybindHandler keybindHandler = new KeybindHandler();

    public void registerAll() {
        EventBus.register(rpcHandler);
        modHandler = new ModHandler();
        commandHandler.loadCommands();
        keybindHandler.loadBinds();
    }

    public RPCHandler getRpcHandler() {
        return rpcHandler;
    }

    public ModHandler getModHandler() {
        return modHandler;
    }

    public CommandHandler getCommandHandler() {
        return commandHandler;
    }

    public NotificationHandler getNotificationHandler() {
        return notificationHandler;
    }

    public KeybindHandler getKeybindHandler() {
        return keybindHandler;
    }
}
