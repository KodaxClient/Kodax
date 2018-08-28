package me.kodingking.kodax.netty;

import me.kodingking.kodax.event.EventListener;
import me.kodingking.kodax.event.events.world.WorldChangeEvent;
import me.kodingking.kodax.event.events.world.WorldChangeEvent.Type;
import me.kodingking.netty.client.NettyClient;
import me.kodingking.netty.packet.impl.client.CPacketLocation;
import net.minecraft.client.Minecraft;

/**
 * @author KodingKing
 */
public class NettyEventListener {

  @EventListener
  public void onWorldChange(WorldChangeEvent e) {
    if (e.getType() != Type.POST) {
      return;
    }

    if (Minecraft.getMinecraft().getCurrentServerData() == null) {
      NettyClient.sendPacket(new CPacketLocation(null));
    } else {
      NettyClient.sendPacket(new CPacketLocation(
          Minecraft.getMinecraft().getCurrentServerData().serverIP + "-" + e.getWorld()
              .getWorldInfo().getWorldName() + "-" + e.getWorld().getWorldInfo().getSeed()));
    }
  }

}
