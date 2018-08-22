package me.kodingking.kodax.mixins.network;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import me.kodingking.kodax.Kodax;
import me.kodingking.kodax.command.Command;
import me.kodingking.kodax.utils.PacketUtils;
import me.kodingking.kodax.utils.PacketUtils.Payload;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiMerchant;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiScreenBook;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.command.CommandBase;
import net.minecraft.entity.IMerchant;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketThreadUtil;
import net.minecraft.network.play.server.S3APacketTabComplete;
import net.minecraft.network.play.server.S3FPacketCustomPayload;
import net.minecraft.village.MerchantRecipeList;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(NetHandlerPlayClient.class)
public class MixinNetHandlerPlayClient {

  @Shadow
  private Minecraft gameController;

  @Shadow
  @Final
  private static Logger logger;

  /**
   * @author KodingKing
   */
  @Overwrite
  public void handleTabComplete(S3APacketTabComplete packetIn) {
    PacketThreadUtil.checkThreadAndEnqueue(packetIn, Minecraft.getMinecraft().thePlayer.sendQueue,
        this.gameController);
    String[] astring = packetIn.func_149630_c();

    List<String> newOptions = new ArrayList<>(Arrays.asList(astring));

    try {
      if (Minecraft.getMinecraft().currentScreen instanceof GuiChat) {
        Field inputField = ((GuiChat) Minecraft.getMinecraft().currentScreen).getClass()
            .getDeclaredField("inputField");
        inputField.setAccessible(true);
        GuiTextField input = (GuiTextField) inputField
            .get((GuiChat) Minecraft.getMinecraft().currentScreen);

        if (input.getText().startsWith("/")) {

          String[] astring1 = input.getText().substring(1).split(" ", -1);
          String s = astring1[0];

          if (astring1.length == 1) {
            for (Command c : Kodax.INSTANCE.getHandlerController().getCommandHandler()
                .getCommands()) {
              if (CommandBase.doesStringStartWith(s, c.getName())) {
                newOptions.add("/" + c.getName());
              }
            }

            astring = newOptions.toArray(new String[0]);
          }
        }
      }
    } catch (NoSuchFieldException | IllegalAccessException e) {
      e.printStackTrace();
    }

    if (this.gameController.currentScreen instanceof GuiChat) {
      GuiChat guichat = (GuiChat) this.gameController.currentScreen;
      guichat.onAutocompleteResponse(astring);
    }
  }

  /**
   * @author KodingKing
   */
  @Overwrite
  public void handleCustomPayload(S3FPacketCustomPayload packetIn) {
    if (Minecraft.getMinecraft().thePlayer == null)
      return;

    PacketThreadUtil.checkThreadAndEnqueue(packetIn, Minecraft.getMinecraft().thePlayer.sendQueue,
        this.gameController);

    if ("MC|TrList".equals(packetIn.getChannelName())) {
      PacketBuffer packetbuffer = packetIn.getBufferData();

      try {
        int i = packetbuffer.readInt();
        GuiScreen guiscreen = this.gameController.currentScreen;

        if (guiscreen != null && guiscreen instanceof GuiMerchant
            && i == this.gameController.thePlayer.openContainer.windowId) {
          IMerchant imerchant = ((GuiMerchant) guiscreen).getMerchant();
          MerchantRecipeList merchantrecipelist = MerchantRecipeList.readFromBuf(packetbuffer);
          imerchant.setRecipes(merchantrecipelist);
        }
      } catch (IOException ioexception) {
        logger.error((String) "Couldn\'t load trade info", (Throwable) ioexception);
      } finally {
        packetbuffer.release();
      }
    } else if ("MC|Brand".equals(packetIn.getChannelName())) {
      this.gameController.thePlayer
          .setClientBrand(packetIn.getBufferData().readStringFromBuffer(32767));
    } else if ("MC|BOpen".equals(packetIn.getChannelName())) {
      ItemStack itemstack = this.gameController.thePlayer.getCurrentEquippedItem();

      if (itemstack != null && itemstack.getItem() == Items.written_book) {
        this.gameController
            .displayGuiScreen(new GuiScreenBook(this.gameController.thePlayer, itemstack, false));
      }
    }
//    else if (packetIn.getChannelName() != null && packetIn.getChannelName()
//        .startsWith("Zyntax|")) {
//      String channelName = packetIn.getChannelName();
//      Payload payload = PacketUtils.getPayload(packetIn);
//      payload.channel = channelName;
//
//      if (payload.channel.equals("Zyntax|Scoreboard")) {
//        int lineCount = ((Double) payload.getMessage().get("line_count")).intValue();
//        List<String> lines = new ArrayList<>();
//        for (int i = 0; i < lineCount; i++) {
//          lines.add((String) payload.getMessage().get("line" + i));
//        }
//        String title = (String) payload.getMessage().get("title");
////        Sidebar.currentLines = lines;
////        Sidebar.currentTitle = title;
//      } else if (payload.channel.equals("Zyntax|TabList")) {
//        UUID uuid = UUID.fromString((String) payload.getMessage().get("uuid"));
//        String display = (String) payload.getMessage().get("display_name");
////        TabList.toRender.put(uuid, display);
//      }
//    }
  }
}
