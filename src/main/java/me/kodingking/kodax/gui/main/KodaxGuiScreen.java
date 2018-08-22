package me.kodingking.kodax.gui.main;

import com.github.fcannizzaro.material.Colors;
import java.awt.Color;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import me.kodingking.kodax.Kodax;
import me.kodingking.kodax.gui.main.elements.GuiElement;
import me.kodingking.kodax.gui.main.sections.HomeSection;
import me.kodingking.kodax.gui.main.sections.MiscSettingsSection;
import me.kodingking.kodax.gui.main.sections.ModSettingsSection;
import me.kodingking.kodax.utils.FontUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Mouse;

public class KodaxGuiScreen extends GuiScreen {

  @Override
  public void initGui() {
    sidebarElements = Arrays.asList(
            new HomeSection(),
            new ModSettingsSection(),
            new MiscSettingsSection()
    );
  }

  private int initialXOffset = 120;
  private int xOffset = initialXOffset;
  private int colorOffset = 150;
  private int fullColorOffset = 255;

  private int elementOffset = 0;
  private double amountToRender;
  private double maxAmountToRender = 0;

  private List<SidebarElement> sidebarElements;
  private SidebarElement selectedScreen;

  @Override
  public void updateScreen() {
    int inc = Math.min(10, xOffset / 5);
    if (xOffset - inc > 0) {
      xOffset -= inc;
    }

    int colorInc = 5;
    if (colorOffset - colorInc >= 0) {
      colorOffset -= colorInc;
    }
    if (fullColorOffset - colorInc >= 0) {
      fullColorOffset -= colorInc;
    }

    if (selectedScreen != null) {
      for (GuiElement e : selectedScreen.getElementList()) {
        e.updateScreen();
      }
    }
  }

  @Override
  public void handleMouseInput() throws IOException {
    super.handleMouseInput();

    int i = Mouse.getEventDWheel();

    int mouseX = Mouse.getX();

    if (i == 0) {
      return;
    }

    if (i > 1) {
      i = Kodax.INSTANCE.getSettings().INVERT_SCROLLING ? -1 : 1;
    } else if (i < -1) {
      i = Kodax.INSTANCE.getSettings().INVERT_SCROLLING ? 1 : -1;
    }

    int sidebarWidth = initialXOffset - xOffset;
    if (mouseX > sidebarWidth * 2) {
      selectedScreen.scroll(i);
    } else {
      scroll(i);
    }
  }

  @Override
  protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
    int sidebarWidth = initialXOffset - xOffset;
    int headerHeight = 20;
    int yCount = headerHeight;
    int elementHeight = 20;
    for (int i = elementOffset; i < amountToRender; i++) {
      SidebarElement element = sidebarElements.get(i);
      boolean hovered = isElementHovered(mouseX, mouseY, headerHeight, elementHeight,
          yCount - headerHeight, sidebarWidth);

      if (hovered) {
        selectedScreen = element;
        Minecraft.getMinecraft().thePlayer.playSound("random.click", 1.0F, 1.0F);
        break;
      }

      yCount += elementHeight;
    }

    super.mouseClicked(mouseX, mouseY, mouseButton);
  }

  @Override
  public void drawScreen(int mouseX, int mouseY, float partialTicks) {
    if (sidebarElements == null) {
      return;
    }
    if (selectedScreen == null) {
      selectedScreen = sidebarElements.get(0);
    }

    ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());

    int sidebarWidth = initialXOffset - xOffset;
    Gui.drawRect(0, 0, sidebarWidth, sr.getScaledHeight(), new Color(0, 0, 0, 240).getRGB());

    int headerHeight = 20;
    Gui.drawRect(0, 0, sidebarWidth, headerHeight, Colors.blue_400.asColor().getRGB());
    fontRendererObj.drawString(Kodax.INSTANCE.getClientName(), 8 - xOffset,
        headerHeight / 2 - fontRendererObj.FONT_HEIGHT / 3, Color.WHITE.getRGB(), false);

    int yCount = headerHeight;
    int elementHeight = 20;

    amountToRender = Math.min(sidebarElements.size(),
        Math.floor(sidebarElements.size() * (height / GuiElement.elementHeight)));
    if (maxAmountToRender == 0) {
      maxAmountToRender = Math.min(sidebarElements.size(),
          Math.floor(sidebarElements.size() * (height / GuiElement.elementHeight)));
    }

    for (int i = elementOffset; i < amountToRender; i++) {
      SidebarElement element = sidebarElements.get(i);

      boolean hovered = isElementHovered(mouseX, mouseY, headerHeight, elementHeight,
          yCount - headerHeight, sidebarWidth);

      if (hovered) {
        Gui.drawRect(0, yCount, sidebarWidth, yCount + elementHeight,
            new Color(255, 255, 255, 100).getRGB());
      } else {
        Gui.drawRect(0, yCount, sidebarWidth, yCount + elementHeight,
            new Color(0, 0, 0, 50).getRGB());
      }

      if (element == selectedScreen && !hovered) {
        Gui.drawRect(0, yCount, sidebarWidth, yCount + elementHeight,
            new Color(41, 121, 255, 150).getRGB());
      }

      drawString(element.getName(), 8 - xOffset,
          yCount + (elementHeight / 2 - fontRendererObj.FONT_HEIGHT / 3));

      yCount += elementHeight;
    }

    Gui.drawRect(sidebarWidth, 0, sr.getScaledWidth(), sr.getScaledHeight(),
        new Color(0, 0, 0, (int) Math.max(0, (150 - colorOffset) * 1.5)).getRGB());
    selectedScreen.renderScreen(mouseX, mouseY, sidebarWidth, 0, sr.getScaledWidth() - sidebarWidth,
        sr.getScaledHeight(), colorOffset, fullColorOffset);

    super.drawScreen(mouseX, mouseY, partialTicks);
  }

  public void scroll(int i) {
    if ((elementOffset == 0 && i == -1) || (elementOffset == sidebarElements.size() && i == 1)) {
      return;
    }
    if (i == 1) {
      elementOffset++;
    } else if (i == -1) {
      elementOffset--;
    }
  }

  public boolean isElementHovered(int mouseX, int mouseY, int headerHeight, int elementHeight,
      int y, int sidebarWidth) {
    return mouseX < sidebarWidth &&
        mouseY >= y + elementHeight &&
        mouseY < y + elementHeight + headerHeight;
  }

  public static void drawString(String text, int x, int y) {
    drawString(text, x, y, false);
  }

  public static void drawString(String text, int x, int y, boolean dropShadow) {
    if (Kodax.INSTANCE.getSettings().SETTINGS_GUI_CHROMA_TEXT) {
      FontUtils.drawChromaString(x, y, text, dropShadow);
    } else {
      Minecraft.getMinecraft().fontRendererObj
          .drawString(text, x, y, Color.WHITE.getRGB(), dropShadow);
    }
  }

}
