package me.kodingking.kodax.mods.autotip;

import me.kodingking.kodax.Kodax;
import me.kodingking.kodax.event.EventListener;
import me.kodingking.kodax.event.events.UpdateEvent;
import me.kodingking.kodax.event.events.network.PacketReceivedEvent;
import me.kodingking.kodax.event.events.world.ServerConnectEvent;
import me.kodingking.kodax.utils.ChatUtils;
import me.kodingking.kodax.utils.server.HypixelUtils;
import net.minecraft.network.login.server.S00PacketDisconnect;

public class AutoTipListener {

    private long nextScheduledTip = 0;

    @EventListener
    public void onServerConnect(ServerConnectEvent e) {
        if (HypixelUtils.isConnectedToHypixel()) {
            nextScheduledTip = System.currentTimeMillis();
        } else {
            nextScheduledTip = 0;
        }
    }

    @EventListener
    public void onUpdate(UpdateEvent e) {
        if (nextScheduledTip != 0 && System.currentTimeMillis() > nextScheduledTip) {
            ChatUtils.addChatMessage("Attempting to tip all.");
            ChatUtils.sendChatMessage("/tipall");
            nextScheduledTip = System.currentTimeMillis() +
                    Kodax.INSTANCE.getHandlerController().getModHandler().getAutoTipMod()
                            .getConfig().INTERVAL * 1000 * 60;
        }
    }

    @EventListener
    public void onPacketReceived(PacketReceivedEvent e) {
        if (!(e.getPacket() instanceof S00PacketDisconnect)) {
            return;
        }
        nextScheduledTip = 0;
    }

}
