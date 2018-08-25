package me.kodingking.kodax.mixins.network;

import io.netty.channel.ChannelHandlerContext;
import me.kodingking.kodax.Kodax;
import me.kodingking.kodax.event.EventBus;
import me.kodingking.kodax.event.events.network.PacketReceivedEvent;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetworkManager.class)
public class MixinNetworkManager {

  @Inject(method = "channelRead0", at = @At(value = "FIELD", target = "Lnet/minecraft/network/NetworkManager;channel:Lio/netty/channel/Channel;", shift = At.Shift.AFTER))
  private void channelRead0(ChannelHandlerContext channelHandlerContext, Packet packet,
      CallbackInfo callbackInfo) {
    PacketReceivedEvent packetReceivedEvent = new PacketReceivedEvent(packet);
    EventBus.call(packetReceivedEvent);
  }

}
