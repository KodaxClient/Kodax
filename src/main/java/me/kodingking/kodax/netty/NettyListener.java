package me.kodingking.kodax.netty;

import io.netty.channel.ChannelHandlerContext;
import me.kodingking.kodax.utils.ChatUtils;
import me.kodingking.netty.client.handler.AbstractPacketHandler;
import me.kodingking.netty.packet.AbstractPacket;
import me.kodingking.netty.packet.impl.projects.kodax.CPacketAnnounce;
import me.kodingking.netty.packet.impl.server.SPacketEncryptRequest;
import me.kodingking.netty.packet.impl.server.SPacketLoginFailure;
import me.kodingking.netty.packet.impl.server.SPacketLoginSuccess;

public class NettyListener extends AbstractPacketHandler {

  @Override
  public void onEncryptRequest(ChannelHandlerContext channelHandlerContext,
      SPacketEncryptRequest sPacketEncryptRequest) {

  }

  @Override
  public void onLoginSuccess(ChannelHandlerContext channelHandlerContext,
      SPacketLoginSuccess sPacketLoginSuccess) {
    System.out.println("Connected to netty successfully.");
  }

  @Override
  public void onLoginFailure(ChannelHandlerContext channelHandlerContext,
      SPacketLoginFailure sPacketLoginFailure) {
    System.out.println("Failed to connect to netty.");
  }

  @Override
  public void onOtherPacket(ChannelHandlerContext channelHandlerContext,
      AbstractPacket abstractPacket) {
    if (abstractPacket instanceof CPacketAnnounce) {
      ChatUtils.addChatMessage(((CPacketAnnounce) abstractPacket).getMessage(), true);
    }
  }
}
