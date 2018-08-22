package me.kodingking.kodax.mixins.gui;

import me.kodingking.kodax.gui.GuiIngameMultiplayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiIngameMenu.class)
public class MixinGuiIngameMenu extends GuiScreen {

  @Inject(method = "initGui", at = @At("RETURN"))
  private void initGui(CallbackInfo callbackInfo) {
    if (!Minecraft.getMinecraft().isSingleplayer()) {
      this.buttonList.add(
          new GuiButton(11, this.width / 2 - 100, this.height / 4 + 144 + -16, "Server Switcher"));
    }
  }

  @Inject(method = "actionPerformed", at = @At("HEAD"))
  private void actionPerformed(GuiButton guiButton, CallbackInfo callbackInfo) {
    switch (guiButton.id) {
      case 11:
        Minecraft.getMinecraft()
            .displayGuiScreen(new GuiIngameMultiplayer(Minecraft.getMinecraft().currentScreen));
        break;
    }
  }

}
