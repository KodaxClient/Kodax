package me.kodingking.kodax;

import com.darkmagician6.eventapi.EventTarget;
import java.util.HashMap;
import java.util.Map;
import me.kodingking.kodax.api.KodaxApi;
import me.kodingking.kodax.events.KeyPressEvent;
import me.kodingking.kodax.events.UpdateEvent;
import me.kodingking.kodax.events.game.GameEndEvent;
import me.kodingking.kodax.events.game.GameWinEvent;
import me.kodingking.kodax.events.gui.GuiOpenEvent;
import me.kodingking.kodax.events.network.PacketReceivedEvent;
import me.kodingking.kodax.events.player.ChatMessageReceivedEvent;
import me.kodingking.kodax.events.world.WorldChangeEvent;
import me.kodingking.kodax.gui.GuiKodaxMainMenu;
import me.kodingking.kodax.gui.main.KodaxGuiScreen;
import me.kodingking.kodax.keybind.XenonKeybind;
import me.kodingking.kodax.utils.GuiUtils;
import me.kodingking.kodax.utils.server.HypixelUtils;
import me.kodingking.kodax.utils.MiscUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;

public class MainListener {

  private Map<Integer, Boolean> keyMap;

  @EventTarget
  public void onUpdate(UpdateEvent e) {
    MiscUtils.update();

    if (keyMap == null) {
      keyMap = new HashMap<>();
    }

    if (Minecraft.getMinecraft().currentScreen == null) {
      for (XenonKeybind xenonKeybind : Kodax.INSTANCE.getHandlerController().getKeybindHandler()
          .getXenonKeybinds()) {
        int key = xenonKeybind.getKeyCode();
        boolean pressed = Keyboard.isKeyDown(key);

        if (pressed) {
          xenonKeybind.onHeld();
        }

        keyMap.putIfAbsent(key, false);
        boolean keyBool = keyMap.get(key);

        if (!keyBool && pressed) {
          xenonKeybind.onPress();
          keyMap.replace(key, true);
        } else if (!pressed && keyBool) {
          xenonKeybind.onRelease();
          keyMap.replace(key, false);
        }
      }
    }
  }

  @EventTarget
  public void onPacketReceived(PacketReceivedEvent e) {
    if (e.getPacket() instanceof S02PacketChat) {
      String message = ((S02PacketChat) e.getPacket()).getChatComponent().getUnformattedText();
      ChatMessageReceivedEvent chatMessageReceivedEvent = new ChatMessageReceivedEvent(message);
      Kodax.EVENT_BUS.call(chatMessageReceivedEvent);

      if (HypixelUtils.hasGameEnded(message)) {
        GameEndEvent gameEndEvent = new GameEndEvent();
        Kodax.EVENT_BUS.call(gameEndEvent);
      }

      if (HypixelUtils.hasWonGame(message)) {
        GameWinEvent gameWinEvent = new GameWinEvent();
        Kodax.EVENT_BUS.call(gameWinEvent);
      }
    }
  }

  @EventTarget
  public void onGuiOpen(GuiOpenEvent e) {
    if (Kodax.INSTANCE.getSettings().BLURRED_GUI_BACKGROUND) {
      if (e.getGuiScreen() == null || e.getGuiScreen() instanceof GuiChat) {
        GuiUtils.unloadShader();
      } else if (Minecraft.getMinecraft().theWorld != null && Minecraft
          .getMinecraft().theWorld.playerEntities.contains(Minecraft.getMinecraft().thePlayer)) {
        GuiUtils
            .applyShader(new ResourceLocation("minecraft", "shaders/post/fade_in_blur.json"));
      }
    }
    if (e.getGuiScreen() instanceof GuiMainMenu && !(e
        .getGuiScreen() instanceof GuiKodaxMainMenu)) {
      e.setCancelled(true);
      Minecraft.getMinecraft().displayGuiScreen(new GuiKodaxMainMenu());
    }
  }

  @EventTarget
  public void onKeyPress(KeyPressEvent e) {
    if (e.getKey() == Keyboard.KEY_GRAVE && !(Minecraft
        .getMinecraft().currentScreen instanceof KodaxGuiScreen)) {
      Minecraft.getMinecraft().displayGuiScreen(new KodaxGuiScreen());
    }
  }

  @EventTarget
  public void onWorldChange(WorldChangeEvent e) {
    KodaxApi.clearCache();
  }

}
