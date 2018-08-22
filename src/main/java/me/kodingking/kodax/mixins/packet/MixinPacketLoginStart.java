package me.kodingking.kodax.mixins.packet;

import me.kodingking.kodax.utils.server.Server;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.login.client.C00PacketLoginStart;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(C00PacketLoginStart.class)
public class MixinPacketLoginStart {

  @Inject(method = "writePacketData", at = @At("RETURN"))
  public void onWritePacketData(PacketBuffer buf, CallbackInfo callbackInfo) {
    if (Server.ZYNTAX.isConneted()) {
      buf.writeByte(2);
    }
  }
}
