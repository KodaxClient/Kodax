package me.kodingking.kodax.mixins.entity;

import com.mojang.authlib.GameProfile;
import me.kodingking.kodax.api.KodaxApi;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractClientPlayer.class)
public abstract class MixinAbstractClientPlayer extends EntityPlayer {

  public MixinAbstractClientPlayer(World worldIn, GameProfile gameProfileIn) {
    super(worldIn, gameProfileIn);
  }

  @Inject(method = "<init>", at = @At("RETURN"))
  private void init(World worldIn, GameProfile playerProfile, CallbackInfo callbackInfo) {
    KodaxApi.downloadCape(getGameProfile());
  }

  @Shadow
  protected abstract NetworkPlayerInfo getPlayerInfo();

  /**
   * @author KodingKing
   */
  @Overwrite
  public ResourceLocation getLocationCape() {
    NetworkPlayerInfo networkplayerinfo = this.getPlayerInfo();

    if (KodaxApi.capeLocations.containsKey(networkplayerinfo.getGameProfile().getId())) {
      return KodaxApi.capeLocations.get(networkplayerinfo.getGameProfile().getId());
    }

    return networkplayerinfo == null ? null : networkplayerinfo.getLocationCape();
  }
}
