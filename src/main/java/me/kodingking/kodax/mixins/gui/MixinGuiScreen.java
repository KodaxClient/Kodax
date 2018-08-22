package me.kodingking.kodax.mixins.gui;

import me.kodingking.kodax.Kodax;
import me.kodingking.kodax.events.MouseClickEvent;
import me.kodingking.kodax.events.gui.GuiScreenDrawEvent;
import me.kodingking.kodax.utils.GuiUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiScreen.class)
public abstract class MixinGuiScreen {

  @Shadow
  protected Minecraft mc;
  @Shadow
  public int height;
  @Shadow
  public int width;

  @Shadow
  public abstract void drawWorldBackground(int tint);

  @Inject(method = "drawScreen", at = @At("HEAD"))
  private void preDrawScreen(int mouseX, int mouseY, float partialTicks,
      CallbackInfo callbackInfo) {
    GuiScreenDrawEvent guiScreenDrawEvent = new GuiScreenDrawEvent(GuiScreenDrawEvent.Type.PRE,
        mouseX, mouseY, partialTicks);
    Kodax.EVENT_BUS.call(guiScreenDrawEvent);
  }

  @Inject(method = "drawScreen", at = @At("RETURN"))
  private void postDrawScreen(int mouseX, int mouseY, float partialTicks,
      CallbackInfo callbackInfo) {
    GuiScreenDrawEvent guiScreenDrawEvent = new GuiScreenDrawEvent(GuiScreenDrawEvent.Type.POST,
        mouseX, mouseY, partialTicks);
    Kodax.EVENT_BUS.call(guiScreenDrawEvent);
  }

  @Inject(method = "mouseClicked", at = @At("HEAD"))
  private void preMouseClicked(int mouseX, int mouseY, int mouseButton, CallbackInfo callbackInfo) {
    MouseClickEvent mouseClickEvent = new MouseClickEvent(mouseButton, mouseX, mouseY,
        MouseClickEvent.Type.PRE);
    Kodax.EVENT_BUS.call(mouseClickEvent);
  }

  @Inject(method = "mouseClicked", at = @At("RETURN"))
  private void postMouseClicked(int mouseX, int mouseY, int mouseButton,
      CallbackInfo callbackInfo) {
    MouseClickEvent mouseClickEvent = new MouseClickEvent(mouseButton, mouseX, mouseY,
        MouseClickEvent.Type.POST);
    Kodax.EVENT_BUS.call(mouseClickEvent);
  }

  /**
   * @author KodingKing
   */
  @Overwrite
  public void drawDefaultBackground() {
    if (Minecraft.getMinecraft().theWorld != null && Minecraft
        .getMinecraft().theWorld.playerEntities.contains(Minecraft.getMinecraft().thePlayer)) {
      GuiUtils.drawIngameGuiGradient();
    } else {
      GuiUtils.drawBG();
    }
  }

  /**
   * @author KodingKing
   */
  @Overwrite
  public void drawBackground(int tint) {
    if (Minecraft.getMinecraft().theWorld != null && Minecraft
        .getMinecraft().theWorld.playerEntities.contains(Minecraft.getMinecraft().thePlayer)) {
      GuiUtils.drawIngameGuiGradient();
    } else {
      GuiUtils.drawBG();
    }
  }

}
