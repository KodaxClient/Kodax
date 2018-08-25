package me.kodingking.kodax.mods.keystrokes;

import me.kodingking.kodax.event.EventListener;
import me.kodingking.kodax.event.events.UpdateEvent;
import me.kodingking.kodax.event.events.render.RenderOverlayEvent;
import me.kodingking.kodax.utils.FontUtils;
import me.kodingking.kodax.utils.MiscUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class KeystrokesListener {

    private KeystrokesMod keystrokesMod;
    //0-100
    private Map<Integer, Integer> fadeMap = new HashMap<>();
    private Map<Integer, Integer> fadeMapMouse = new HashMap<>();
    private FontRenderer fontRenderer;

    KeystrokesListener(KeystrokesMod keystrokesMod) {
        this.keystrokesMod = keystrokesMod;
    }

    @EventListener
    public void onRenderOverlay(RenderOverlayEvent e) {
        if (!keystrokesMod.getConfig().ENABLED) {
            return;
        }
        if (e.getType() != RenderOverlayEvent.Type.POST) {
            return;
        }

        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());

        fontRenderer = Minecraft.getMinecraft().fontRendererObj;
        int spacing = keystrokesMod.getConfig().SPACING;
        int scale = (int) (keystrokesMod.getConfig().SCALE / 3.5);
        int level = 0;

        int xOffset = 0;
        int yOffset = 0;

        switch (keystrokesMod.getConfig().CORNER) {
            case 1:
                xOffset = 0;
                yOffset = 0;
                break;
            case 2:
                xOffset = sr.getScaledWidth() - spacing * 2 - scale * 3;
                yOffset = 0;
                break;
            case 3:
                xOffset = 0;
                yOffset =
                        sr.getScaledHeight() - scale * 2 - spacing - (keystrokesMod.getConfig().DISPLAY_SPACE ?
                                scale + spacing : 0) - (keystrokesMod.getConfig().DISPLAY_MOUSE_BUTTONS ? scale
                                + spacing : 0) - (keystrokesMod.getConfig().DISPLAY_CPS ? scale / 2 + spacing : 0);
                break;
            case 4:
                xOffset = sr.getScaledWidth() - spacing * 2 - scale * 3;
                yOffset =
                        sr.getScaledHeight() - scale * 2 - spacing - (keystrokesMod.getConfig().DISPLAY_SPACE ?
                                scale + spacing : 0) - (keystrokesMod.getConfig().DISPLAY_MOUSE_BUTTONS ? scale
                                + spacing : 0) - (keystrokesMod.getConfig().DISPLAY_CPS ? scale / 2 + spacing : 0);
                break;
        }

        renderKey(xOffset + scale + spacing, yOffset, scale, scale, Keyboard.KEY_W, "W");
        level++;
        renderKey(xOffset, yOffset + level * (scale + spacing), scale, scale, Keyboard.KEY_A, "A");
        renderKey(xOffset + scale + spacing, yOffset + level * (scale + spacing), scale, scale,
                Keyboard.KEY_S, "S");
        renderKey(xOffset + (scale + spacing) * 2, yOffset + level * (scale + spacing), scale, scale,
                Keyboard.KEY_D, "D");
        if (keystrokesMod.getConfig().DISPLAY_SPACE) {
            level++;
            renderSpace(xOffset, yOffset + (scale + spacing) * level, scale * 3 + spacing * 2, scale,
                    "--------");
        }
        if (keystrokesMod.getConfig().DISPLAY_MOUSE_BUTTONS) {
            level++;
            renderMouse(xOffset, yOffset + (scale + spacing) * level,
                    (int) (scale * 1.5 + spacing), scale, 0, "LMB");
            renderMouse(xOffset + (int) (scale * 1.5 + spacing * 3), yOffset + (scale + spacing) * level,
                    (int) (scale * 1.5), scale, 1, "RMB");
        }
        if (keystrokesMod.getConfig().DISPLAY_CPS) {
            level++;
            renderText(xOffset, yOffset + (scale + spacing) * level, scale * 3 + spacing * 2, scale / 2,
                    "CPS: " + MiscUtils
                            .getLeftCps());
        }
    }

    @EventListener
    public void onUpdate(UpdateEvent e) {
        int[] keyCodes = new int[]{Keyboard.KEY_W, Keyboard.KEY_A, Keyboard.KEY_S, Keyboard.KEY_D,
                Keyboard.KEY_SPACE};
        int[] keyCodesMouse = new int[]{0, 1};
        for (int keyCode : keyCodes) {
            int currentFade = fadeMap.get(keyCode);
            if (Keyboard.isKeyDown(keyCode)) {
                fadeMap.replace(keyCode,
                        Math.min(200, currentFade + keystrokesMod.getConfig().FADE_INCREMENT * 14));
            }
            if (!Keyboard.isKeyDown(keyCode)) {
                fadeMap.replace(keyCode,
                        Math.max(0, currentFade - keystrokesMod.getConfig().FADE_INCREMENT * 14));
            }
        }
        for (int buttonCode : keyCodesMouse) {
            int currentFade = fadeMapMouse.get(buttonCode);
            if (Mouse.isButtonDown(buttonCode)) {
                fadeMapMouse.replace(buttonCode,
                        Math.min(200, currentFade + keystrokesMod.getConfig().FADE_INCREMENT * 14));
            }
            if (!Mouse.isButtonDown(buttonCode)) {
                fadeMapMouse.replace(buttonCode,
                        Math.max(0, currentFade - keystrokesMod.getConfig().FADE_INCREMENT * 14));
            }
        }
    }

    private void renderSpace(int x, int y, int width, int height, String text) {
        fadeMap.putIfAbsent(Keyboard.KEY_SPACE, 100);
        Gui.drawRect(x, y, x + width, y + height,
                assembleColor(fadeMap.get(Keyboard.KEY_SPACE)).getRGB());
        if (keystrokesMod.getConfig().MODE.equalsIgnoreCase("CHROMA")) {
            FontUtils
                    .drawChromaString(x + (width - fontRenderer.getStringWidth(text)) / 2,
                            y + height / 2 - fontRenderer.FONT_HEIGHT / 3, text,
                            keystrokesMod.getConfig().DROP_SHADOW, EnumChatFormatting.STRIKETHROUGH);
        } else if (keystrokesMod.getConfig().MODE.endsWith("RGB")) {
            fontRenderer.drawString(EnumChatFormatting.STRIKETHROUGH + text,
                    x + (width - fontRenderer.getStringWidth(text)) / 2,
                    y + height / 2 - fontRenderer.FONT_HEIGHT / 3, getRgbColor().getRGB(),
                    keystrokesMod.getConfig().DROP_SHADOW);
        }
    }

    private void renderKey(int x, int y, int width, int height, int keyCode, String text) {
        fadeMap.putIfAbsent(keyCode, 100);
        Gui.drawRect(x, y, x + width, y + height, assembleColor(fadeMap.get(keyCode)).getRGB());
        if (keystrokesMod.getConfig().MODE.equalsIgnoreCase("CHROMA")) {
            FontUtils
                    .drawChromaString(x + (width - fontRenderer.getStringWidth(text)) / 2,
                            y + height / 2 - fontRenderer.FONT_HEIGHT / 3, text,
                            keystrokesMod.getConfig().DROP_SHADOW);
        } else if (keystrokesMod.getConfig().MODE.endsWith("RGB")) {
            fontRenderer.drawString(text, x + (width - fontRenderer.getStringWidth(text)) / 2,
                    y + height / 2 - fontRenderer.FONT_HEIGHT / 3, getRgbColor().getRGB(),
                    keystrokesMod.getConfig().DROP_SHADOW);
        }
    }

    private void renderMouse(int x, int y, int width, int height, int buttonCode, String text) {
        fadeMapMouse.putIfAbsent(buttonCode, 100);
        Gui.drawRect(x, y, x + width, y + height, assembleColor(fadeMapMouse.get(buttonCode)).getRGB());
        if (keystrokesMod.getConfig().MODE.equalsIgnoreCase("CHROMA")) {
            FontUtils.drawChromaString(x + (width - fontRenderer.getStringWidth(text)) / 2,
                    y + height / 2 - fontRenderer.FONT_HEIGHT / 3, text,
                    keystrokesMod.getConfig().DROP_SHADOW);
        } else if (keystrokesMod.getConfig().MODE.endsWith("RGB")) {
            fontRenderer.drawString(text, x + (width - fontRenderer.getStringWidth(text)) / 2,
                    y + height / 2 - fontRenderer.FONT_HEIGHT / 3, getRgbColor().getRGB(),
                    keystrokesMod.getConfig().DROP_SHADOW);
        }
    }

    private void renderText(int x, int y, int width, int height, String text) {
        Gui.drawRect(x, y, x + width, y + height, assembleColor(0).getRGB());
        if (keystrokesMod.getConfig().MODE.equalsIgnoreCase("CHROMA")) {
            FontUtils.drawChromaString(x + (width - fontRenderer.getStringWidth(text)) / 2,
                    y + height / 2 - fontRenderer.FONT_HEIGHT / 3, text,
                    keystrokesMod.getConfig().DROP_SHADOW);
        } else if (keystrokesMod.getConfig().MODE.endsWith("RGB")) {
            fontRenderer.drawString(text, x + (width - fontRenderer.getStringWidth(text)) / 2,
                    y + height / 2 - fontRenderer.FONT_HEIGHT / 3, getRgbColor().getRGB(),
                    keystrokesMod.getConfig().DROP_SHADOW);
        }
    }

    private Color assembleColor(int value) {
        return new Color(value, value, value, 150);
    }

    private Color getRgbColor() {
        return new Color(keystrokesMod.getConfig().COLOR_R, keystrokesMod.getConfig().COLOR_G,
                keystrokesMod.getConfig().COLOR_B, 200);
    }

}
