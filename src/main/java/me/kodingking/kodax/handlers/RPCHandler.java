package me.kodingking.kodax.handlers;

import com.jagrosh.discordipc.IPCClient;
import com.jagrosh.discordipc.entities.DiscordBuild;
import com.jagrosh.discordipc.entities.RichPresence;
import com.jagrosh.discordipc.exceptions.NoDiscordClientException;
import me.kodingking.kodax.Kodax;
import me.kodingking.kodax.event.EventListener;
import me.kodingking.kodax.event.events.InitialisationEvent;
import me.kodingking.kodax.event.events.ShutdownEvent;
import me.kodingking.kodax.event.events.UpdateEvent;
import me.kodingking.kodax.event.events.gui.GuiOpenEvent;
import me.kodingking.kodax.event.events.world.ServerConnectEvent;
import me.kodingking.kodax.event.events.world.WorldChangeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiDownloadTerrain;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.multiplayer.GuiConnecting;

import java.time.OffsetDateTime;

public class RPCHandler {

    private IPCClient ipcClient = new IPCClient(465027275105107979L);
    private boolean tabbedOut = false;
    private OffsetDateTime startTime = OffsetDateTime.now();

    private RichPresence.Builder getDefaultPresence() {
        return new RichPresence.Builder()
                .setState("User: " + Minecraft.getMinecraft().getSession().getUsername())
                .setSmallImage("small_logo_new", Kodax.INSTANCE.getClientName())
                .setStartTimestamp(startTime);
    }

    @EventListener
    public void onInitialisation(InitialisationEvent e) {
        if (e.getType() == InitialisationEvent.Type.PRE) {
            try {
                Kodax.INSTANCE.getLogger().info("Loading rich presence...");
                ipcClient.connect(DiscordBuild.ANY);
                ipcClient.sendRichPresence(
                        new RichPresence.Builder()
                                .setState("User: " + Minecraft.getMinecraft().getSession().getUsername())
                                .setStartTimestamp(startTime)
                                .setDetails("Just opened")
                                .setState(Kodax.INSTANCE.getClientName())
                                .setLargeImage("large_logo_new", Kodax.INSTANCE.getClientName())
                                .build()
                );
            } catch (NoDiscordClientException e1) {
                Kodax.INSTANCE.getLogger().error("Not connecting Discord because it is not open.");
            }
        } else if (e.getType() == InitialisationEvent.Type.POST) {
            try {
                if (!Kodax.INSTANCE.getSettings().DISCORD_RPC) {
                    ipcClient.close();
                    return;
                }
                ipcClient.sendRichPresence(
                        new RichPresence.Builder()
                                .setState("User: " + Minecraft.getMinecraft().getSession().getUsername())
                                .setStartTimestamp(startTime)
                                .setDetails("On the main menu")
                                .setLargeImage("large_logo_new", Kodax.INSTANCE.getClientName())
                                .build()
                );
            } catch (IllegalStateException ignored) {
            }
        }
    }

    @EventListener
    public void onGuiOpen(GuiOpenEvent e) {
        if (!Kodax.INSTANCE.getSettings().DISCORD_RPC) {
            return;
        }
        if (e.getGuiScreen() instanceof GuiMainMenu) {
            try {
                ipcClient.sendRichPresence(
                        new RichPresence.Builder()
                                .setState("User: " + Minecraft.getMinecraft().getSession().getUsername())
                                .setStartTimestamp(startTime)
                                .setDetails("On the main menu")
                                .setLargeImage("large_logo_new", Kodax.INSTANCE.getClientName())
                                .build()
                );
            } catch (IllegalStateException ignored) {
            }
        } else if (e.getGuiScreen() instanceof GuiIngameMenu) {
            try {
                ipcClient.sendRichPresence(
                        getDefaultPresence()
                                .setDetails("Tabbed out")
                                .setLargeImage("away", "Away")
                                .build()
                );
                tabbedOut = true;
            } catch (IllegalStateException ignored) {
            }
        } else if (e.getGuiScreen() instanceof GuiChat) {
            tabbedOut = true;
            ipcClient.sendRichPresence(
                    getDefaultPresence()
                            .setDetails("Typing in chat")
                            .setLargeImage("chat", "Chatting")
                            .build()
            );
        } else {
            if (e.getGuiScreen() == null ||
                    e.getGuiScreen() instanceof GuiIngameMenu ||
                    e.getGuiScreen() instanceof GuiDownloadTerrain ||
                    e.getGuiScreen() instanceof GuiChat ||
                    e.getGuiScreen() instanceof GuiConnecting) {
                return;
            }
            tabbedOut = true;
            ipcClient.sendRichPresence(
                    new RichPresence.Builder()
                            .setState("User: " + Minecraft.getMinecraft().getSession().getUsername())
                            .setStartTimestamp(startTime)
                            .setDetails("Browsing the menu's")
                            .setLargeImage("large_logo_new", Kodax.INSTANCE.getClientName())
                            .build()
            );
        }
    }

    @EventListener
    public void onUpdate(UpdateEvent e) {
        if (!Kodax.INSTANCE.getSettings().DISCORD_RPC) {
            return;
        }
        if (tabbedOut && Minecraft.getMinecraft().currentScreen == null) {
            tabbedOut = false;
            if (!Minecraft.getMinecraft().isSingleplayer()) {
                onServerJoin(new ServerConnectEvent(null, Minecraft.getMinecraft().getCurrentServerData()));
            } else {
                ipcClient.sendRichPresence(
                        getDefaultPresence()
                                .setDetails("Playing single player")
                                .setLargeImage("single_player", "Single Player")
                                .build()
                );
            }
        }
    }

    @EventListener
    public void onWorldChange(WorldChangeEvent e) {
        if (e.getType() != WorldChangeEvent.Type.POST) {
            return;
        }
        if (!Kodax.INSTANCE.getSettings().DISCORD_RPC) {
            return;
        }
        tabbedOut = false;
        if ((Minecraft.getMinecraft().getIntegratedServer() != null && !Minecraft.getMinecraft()
                .getIntegratedServer().getPublic()) || Minecraft.getMinecraft().isSingleplayer() || (
                Minecraft.getMinecraft().getCurrentServerData() != null && Minecraft.getMinecraft()
                        .getCurrentServerData().func_181041_d())) {
            try {
                ipcClient.sendRichPresence(
                        getDefaultPresence()
                                .setDetails("Playing single player")
                                .setLargeImage("single_player", "Single Player")
                                .build()
                );
            } catch (IllegalStateException ignored) {
            }
        }
    }

    @EventListener
    public void onServerJoin(ServerConnectEvent serverConnectEvent) {
        if (!Kodax.INSTANCE.getSettings().DISCORD_RPC) {
            return;
        }
        if (serverConnectEvent.getServerData().serverIP.toLowerCase().endsWith("hypixel.net")) {
            try {
                ipcClient.sendRichPresence(
                        getDefaultPresence()
                                .setDetails("On Hypixel")
                                .setLargeImage("hypixel", "Hypixel Server")
                                .build()
                );
            } catch (IllegalStateException ignored) {
            }
        } else {
            try {
                ipcClient.sendRichPresence(
                        getDefaultPresence()
                                .setDetails("On a server")
                                .setLargeImage("default_server_icon", "Any server")
                                .build()
                );
            } catch (IllegalStateException ignored) {
            }
        }
    }

    @EventListener
    public void onShutdown(ShutdownEvent e) {
        try {
            Kodax.INSTANCE.getLogger().info("Closing rich presence...");
            ipcClient.close();
        } catch (IllegalStateException e1) {
            Kodax.INSTANCE.getLogger().info("Not closing rich presence because it didn't connect.");
        }
    }

}
