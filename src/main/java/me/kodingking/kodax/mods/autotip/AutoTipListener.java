package me.kodingking.kodax.mods.autotip;

import com.darkmagician6.eventapi.EventTarget;
import me.kodingking.kodax.Kodax;
import me.kodingking.kodax.events.UpdateEvent;
import me.kodingking.kodax.events.network.PacketReceivedEvent;
import me.kodingking.kodax.events.world.ServerConnectEvent;
import me.kodingking.kodax.utils.ChatUtils;
import me.kodingking.kodax.utils.server.HypixelUtils;
import net.minecraft.network.login.server.S00PacketDisconnect;

public class AutoTipListener {

  private long nextScheduledTip = 0;

  @EventTarget
  public void onServerConnect(ServerConnectEvent e) {
    if (HypixelUtils.isConnectedToHypixel()) {
      nextScheduledTip = System.currentTimeMillis();
    } else {
      nextScheduledTip = 0;
    }
  }

  @EventTarget
  public void onUpdate(UpdateEvent e) {
    if (nextScheduledTip != 0 && System.currentTimeMillis() > nextScheduledTip) {
      ChatUtils.addChatMessage("Attempting to tip all.");
      ChatUtils.sendChatMessage("/tipall");
      nextScheduledTip = System.currentTimeMillis() +
          Kodax.INSTANCE.getHandlerController().getModHandler().getAutoTipMod()
              .getConfig().INTERVAL * 1000 * 60;
    }
  }

  @EventTarget
  public void onPacketReceived(PacketReceivedEvent e) {
    if (!(e.getPacket() instanceof S00PacketDisconnect)) {
      return;
    }
    nextScheduledTip = 0;
  }

}
