package me.kodingking.kodax.mixins;

import me.kodingking.kodax.Kodax;
import me.kodingking.kodax.SplashScreen;
import me.kodingking.kodax.events.InitialisationEvent;
import me.kodingking.kodax.events.KeyPressEvent;
import me.kodingking.kodax.events.gui.GuiOpenEvent;
import me.kodingking.kodax.events.world.WorldChangeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.texture.TextureManager;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public abstract class MixinMinecraft {

  @Inject(method = "createDisplay", at = @At(value = "INVOKE", target = "Lorg/lwjgl/opengl/Display;setTitle(Ljava/lang/String;)V", shift = At.Shift.AFTER))
  private void createDisplay(CallbackInfo callbackInfo) {
    Display.setTitle("[STARTING] " + Kodax.INSTANCE.getClientName() + " // " + Kodax.INSTANCE
        .getClientVersion());
  }

  @Inject(method = "startGame", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;drawSplashScreen(Lnet/minecraft/client/renderer/texture/TextureManager;)V", shift = At.Shift.AFTER))
  private void loadingStartGame1(CallbackInfo callbackInfo) {
    SplashScreen.advanceProgress("Starting to load...");
  }

  @Inject(method = "startGame", at = @At(value = "NEW", target = "net/minecraft/client/audio/SoundHandler", shift = At.Shift.AFTER))
  private void loadingStartGame2(CallbackInfo callbackInfo) {
    SplashScreen.advanceProgress("Initializing sound handler...");
  }

  @Inject(method = "startGame", at = @At(value = "NEW", target = "net/minecraft/client/gui/FontRenderer", shift = At.Shift.AFTER))
  private void loadingStartGame3(CallbackInfo callbackInfo) {
    SplashScreen.advanceProgress("Initializing font renderer...");
  }

  @Inject(method = "startGame", at = @At(value = "NEW", target = "net/minecraft/client/resources/model/ModelManager", shift = At.Shift.AFTER))
  private void loadingStartGame4(CallbackInfo callbackInfo) {
    SplashScreen.advanceProgress("Loading model manager...");
  }

  @Inject(method = "startGame", at = @At(value = "NEW", target = "net/minecraft/client/renderer/entity/RenderManager", shift = At.Shift.AFTER))
  private void loadingStartGame5(CallbackInfo callbackInfo) {
    SplashScreen.advanceProgress("Loading render manager...");
  }

  @Inject(method = "startGame", at = @At(value = "NEW", target = "net/minecraft/client/renderer/EntityRenderer", shift = At.Shift.AFTER))
  private void loadingStartGame6(CallbackInfo callbackInfo) {
    SplashScreen.advanceProgress("Loading entity renderer...");
  }

  @Inject(method = "startGame", at = @At(value = "NEW", target = "net/minecraft/client/renderer/RenderGlobal", shift = At.Shift.AFTER))
  private void loadingStartGame7(CallbackInfo callbackInfo) {
    SplashScreen.advanceProgress("Loading render global...");
  }

  @Inject(method = "startGame", at = @At(value = "NEW", target = "net/minecraft/client/particle/EffectRenderer", shift = At.Shift.AFTER))
  private void loadingStartGame8(CallbackInfo callbackInfo) {
    SplashScreen.advanceProgress("Loading effect renderer...");
  }


  @Inject(method = "startGame", at = @At(value = "NEW", target = "net/minecraft/client/gui/GuiIngame", shift = At.Shift.AFTER))
  private void preStartGame(CallbackInfo callbackInfo) {
    Kodax.INSTANCE.preStart();

    InitialisationEvent initialisationEvent = new InitialisationEvent(InitialisationEvent.Type.PRE);
    Kodax.EVENT_BUS.call(initialisationEvent);
  }

  @Inject(method = "startGame", at = @At("RETURN"))
  private void postStartGame(CallbackInfo callbackInfo) {
    Kodax.INSTANCE.postStart();

    InitialisationEvent initialisationEvent = new InitialisationEvent(
        InitialisationEvent.Type.POST);
    Kodax.EVENT_BUS.call(initialisationEvent);
  }

  @Inject(method = "dispatchKeypresses", at = @At(value = "INVOKE", target = "Lorg/lwjgl/input/Keyboard;getEventKeyState()Z", shift = At.Shift.AFTER))
  private void dispatchKeypresses(CallbackInfo callbackInfo) {
    int i = Keyboard.getEventKey() == 0 ? Keyboard.getEventCharacter() : Keyboard.getEventKey();
    KeyPressEvent keyPressEvent = new KeyPressEvent(i);
    Kodax.EVENT_BUS.call(keyPressEvent);
  }

  @Inject(method = "displayGuiScreen", at = @At("HEAD"), cancellable = true)
  private void displayGuiScreen(GuiScreen guiScreen, CallbackInfo callbackInfo) {
    GuiOpenEvent guiOpenEvent = new GuiOpenEvent(guiScreen);
    Kodax.EVENT_BUS.call(guiOpenEvent);
    if (guiOpenEvent.isCancelled()) {
      callbackInfo.cancel();
    }
  }

  @Inject(method = "loadWorld(Lnet/minecraft/client/multiplayer/WorldClient;Ljava/lang/String;)V", at = @At("HEAD"))
  private void preLoadWorld(WorldClient worldClientIn, String loadingMessage,
      CallbackInfo callbackInfo) {
    WorldChangeEvent worldChangeEvent = new WorldChangeEvent(worldClientIn, loadingMessage,
        WorldChangeEvent.Type.PRE);
    Kodax.EVENT_BUS.call(worldChangeEvent);
  }

  @Inject(method = "loadWorld(Lnet/minecraft/client/multiplayer/WorldClient;Ljava/lang/String;)V", at = @At("RETURN"))
  private void postLoadWorld(WorldClient worldClientIn, String loadingMessage,
      CallbackInfo callbackInfo) {
    WorldChangeEvent worldChangeEvent = new WorldChangeEvent(worldClientIn, loadingMessage,
        WorldChangeEvent.Type.POST);
    Kodax.EVENT_BUS.call(worldChangeEvent);
  }

  /**
   * @author KodingKing
   */
  @Overwrite
  public void drawSplashScreen(TextureManager textureManagerInstance) throws LWJGLException {
    SplashScreen.render(textureManagerInstance);
  }
}
