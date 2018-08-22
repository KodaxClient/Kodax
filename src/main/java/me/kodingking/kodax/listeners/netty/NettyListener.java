package me.kodingking.kodax.listeners.netty;

import io.netty.channel.ChannelHandlerContext;
import me.kodingking.kodax.utils.ChatColor;
import me.kodingking.kodax.utils.ChatUtils;
import me.kodingking.kodaxnetty.client.IClientListener;
import me.kodingking.kodaxnetty.packet.AdminAnnouncePacket;
import me.kodingking.kodaxnetty.packet.BasePacket;

public class NettyListener implements IClientListener {

  @Override
  public void packetReceived(ChannelHandlerContext channelHandlerContext, BasePacket basePacket) {
    if (basePacket instanceof AdminAnnouncePacket)
      ChatUtils.addChatMessage(ChatColor.RED + ((AdminAnnouncePacket) basePacket).getMessage());
  }
}
