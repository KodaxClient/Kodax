package me.kodingking.kodax.mods.keystrokes.gui;

import java.awt.Color;
import java.io.IOException;
import me.kodingking.kodax.gui.components.GuiSlider;
import me.kodingking.kodax.mods.keystrokes.KeystrokesConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;

public class ConfigGui extends GuiScreen {

  private KeystrokesConfig config;

  private GuiSlider scaleSlider, fadeIncSlider, spacingSlider, colorRSlider, colorGSlider, colorBSlider;
  private GuiButton enabledButton, modeButton, dropShadowButton, displaySpaceButton, displayMouseButtonsButton, displayCpsButton, cornerButton;

  public ConfigGui(KeystrokesConfig config) {
    this.config = config;
  }

  @Override
  public void initGui() {
    int width = 150;
    int height = 20;
    int yPos = 30;
    int spacing = 5;
    ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());

    yPos += height + spacing;
    enabledButton = new GuiButton(1, (sr.getScaledWidth() - width) / 2, yPos, width, height,
        "Enabled: " + config.ENABLED);
    this.buttonList.add(enabledButton);

    yPos += height + spacing;
    scaleSlider = new GuiSlider(2, (sr.getScaledWidth() - width) / 2, yPos, width, height,
        "Scale: ", "", 75, 200, config.SCALE, false, true);
    this.buttonList.add(scaleSlider);

    yPos += height + spacing;
    fadeIncSlider = new GuiSlider(3, (sr.getScaledWidth() - width) / 2, yPos, width, height,
        "Fade Increment: ", "", 1, 7, config.FADE_INCREMENT, false, true);
    this.buttonList.add(fadeIncSlider);

    yPos += height + spacing;
    spacingSlider = new GuiSlider(4, (sr.getScaledWidth() - width) / 2, yPos, width, height,
        "Spacing: ", "", 0, 15, config.SPACING, false, true);
    this.buttonList.add(spacingSlider);

    yPos += height + spacing;
    modeButton = new GuiButton(5, (sr.getScaledWidth() - width) / 2, yPos, width, height,
        "Mode: " + config.MODE);
    this.buttonList.add(modeButton);

    yPos += height + spacing;
    cornerButton = new GuiButton(10, (sr.getScaledWidth() - width) / 2, yPos, width, height,
        "Corner: " + config.getCorner());
    this.buttonList.add(cornerButton);

    yPos += height + spacing;
    dropShadowButton = new GuiButton(6, (sr.getScaledWidth() - width) / 2, yPos, width, height,
        "Drop Shadow: " + config.DROP_SHADOW);
    this.buttonList.add(dropShadowButton);

    yPos += height + spacing;
    displaySpaceButton = new GuiButton(7, (sr.getScaledWidth() - width) / 2, yPos, width, height,
        "Display Space Bar: " + config.DISPLAY_SPACE);
    this.buttonList.add(displaySpaceButton);

    yPos += height + spacing;
    displayMouseButtonsButton = new GuiButton(8, (sr.getScaledWidth() - width) / 2, yPos, width,
        height, "Display Mouse Buttons: " + config.DISPLAY_MOUSE_BUTTONS);
    this.buttonList.add(displayMouseButtonsButton);

    yPos += height + spacing;
    displayCpsButton = new GuiButton(9, (sr.getScaledWidth() - width) / 2, yPos, width, height,
        "Display CPS Button: " + config.DISPLAY_CPS);
    this.buttonList.add(displayCpsButton);

    yPos += height + spacing;
    colorRSlider = new GuiSlider(3, (sr.getScaledWidth() - width) / 2, yPos, width, height,
        "Color R: ", "", 0, 255, config.COLOR_R, false, true);
    this.buttonList.add(colorRSlider);

    yPos += height + spacing;
    colorGSlider = new GuiSlider(3, (sr.getScaledWidth() - width) / 2, yPos, width, height,
        "Color G: ", "", 0, 255, config.COLOR_G, false, true);
    this.buttonList.add(colorGSlider);

    yPos += height + spacing;
    colorBSlider = new GuiSlider(3, (sr.getScaledWidth() - width) / 2, yPos, width, height,
        "Color B: ", "", 0, 255, config.COLOR_B, false, true);
    this.buttonList.add(colorBSlider);

    if (config.MODE.equalsIgnoreCase("RGB")) {
      colorRSlider.visible = true;
      colorGSlider.visible = true;
      colorBSlider.visible = true;
    } else {
      colorRSlider.visible = false;
      colorGSlider.visible = false;
      colorBSlider.visible = false;
    }
  }

  @Override
  public void updateScreen() {
    config.SCALE = scaleSlider.getValueInt();
    config.FADE_INCREMENT = fadeIncSlider.getValueInt();
    config.SPACING = spacingSlider.getValueInt();

    if (config.MODE.equalsIgnoreCase("RGB")) {
      colorRSlider.visible = true;
      colorGSlider.visible = true;
      colorBSlider.visible = true;

      config.COLOR_R = colorRSlider.getValueInt();
      config.COLOR_G = colorGSlider.getValueInt();
      config.COLOR_B = colorBSlider.getValueInt();
    } else {
      colorRSlider.visible = false;
      colorGSlider.visible = false;
      colorBSlider.visible = false;
    }

    super.updateScreen();
  }

  @Override
  protected void actionPerformed(GuiButton button) throws IOException {
    switch (button.id) {
      case 1:
        config.ENABLED = !config.ENABLED;
        enabledButton.displayString = "Enabled: " + config.ENABLED;
        break;
      case 5:
        config.cycleMode();
        modeButton.displayString = "Mode: " + config.MODE;
        break;
      case 6:
        config.DROP_SHADOW = !config.DROP_SHADOW;
        dropShadowButton.displayString = "Drop Shadow: " + config.DROP_SHADOW;
        break;
      case 7:
        config.DISPLAY_SPACE = !config.DISPLAY_SPACE;
        displaySpaceButton.displayString = "Display Space Bar: " + config.DISPLAY_SPACE;
        break;
      case 8:
        config.DISPLAY_MOUSE_BUTTONS = !config.DISPLAY_MOUSE_BUTTONS;
        displayMouseButtonsButton.displayString =
            "Display Mouse Buttons: " + config.DISPLAY_MOUSE_BUTTONS;
        break;
      case 9:
        config.DISPLAY_CPS = !config.DISPLAY_CPS;
        displayCpsButton.displayString = "Display CPS Button: " + config.DISPLAY_CPS;
        break;
      case 10:
        int corner = config.CORNER + 1;
        if (corner > 4) {
          corner = 1;
        }
        config.CORNER = corner;
        cornerButton.displayString = config.getCorner();
        break;
    }
    super.actionPerformed(button);
  }

  @Override
  public void drawScreen(int mouseX, int mouseY, float partialTicks) {
    FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;
    ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());

    int textScale = 2;
    GlStateManager.pushMatrix();
    GlStateManager.scale(textScale, textScale, textScale);
    String titleLabel = "Keystrokes Config";
    fr.drawString(titleLabel, (float) (sr.getScaledWidth() / 4 - fr.getStringWidth(titleLabel) / 2),
        10, Color.WHITE.getRGB(), true);
    GlStateManager.scale(1 / textScale, 1 / textScale, 1 / textScale);
    GlStateManager.popMatrix();

    super.drawScreen(mouseX, mouseY, partialTicks);
  }
}
