package me.kodingking.kodax.mixins.entity;

import me.kodingking.kodax.Kodax;
import me.kodingking.kodax.events.UpdateEvent;
import me.kodingking.kodax.events.player.ChatMessageSendEvent;
import net.minecraft.client.entity.EntityPlayerSP;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityPlayerSP.class)
public class MixinEntityPlayerSP {

  @Inject(method = "onUpdate", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/AbstractClientPlayer;onUpdate()V", shift = At.Shift.BEFORE))
  private void onUpdate(CallbackInfo callbackInfo) {
    UpdateEvent updateEvent = new UpdateEvent();
    Kodax.EVENT_BUS.call(updateEvent);
  }

  @Inject(method = "sendChatMessage", at = @At("HEAD"), cancellable = true)
  private void sendChatMessage(String message, CallbackInfo callbackInfo) {
    ChatMessageSendEvent chatMessageSendEvent = new ChatMessageSendEvent(message);
    Kodax.EVENT_BUS.call(chatMessageSendEvent);
    if (chatMessageSendEvent.isCancelled()) {
      callbackInfo.cancel();
    }
  }

}
