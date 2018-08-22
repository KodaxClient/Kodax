package me.kodingking.kodax.events.world;

import com.darkmagician6.eventapi.events.Event;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.ServerData;

public class ServerConnectEvent implements Event {

    private GuiScreen guiScreen;
    private ServerData serverData;

    public ServerConnectEvent(GuiScreen guiScreen, ServerData serverData) {
        this.guiScreen = guiScreen;
        this.serverData = serverData;
    }

    public GuiScreen getGuiScreen() {
        return guiScreen;
    }

    public ServerData getServerData() {
        return serverData;
    }
}
