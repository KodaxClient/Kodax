package me.kodingking.kodax.mods.togglesprint;

import me.kodingking.kodax.Kodax;
import me.kodingking.kodax.event.EventBus;
import me.kodingking.kodax.event.EventListener;
import me.kodingking.kodax.event.events.UpdateEvent;
import me.kodingking.kodax.event.events.render.RenderOverlayEvent;
import me.kodingking.kodax.mods.core.AbstractCoreMod;
import me.kodingking.kodax.mods.togglesprint.config.ToggleSprintConfig;
import me.kodingking.kodax.utils.FontUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

import java.awt.*;

@AbstractCoreMod.Meta(name = "Toggle Sprint", version = "1.0", authors = {"KodingKing"})
public class ToggleSprintMod extends AbstractCoreMod {

    private ToggleSprintConfig config;

    @Override
    public void onLoad() {
        EventBus.register(this);
        Kodax.CONFIG.register(config = new ToggleSprintConfig());
    }

    @Override
    public void onClose() {

    }

    @EventListener
    public void onUpdate(UpdateEvent e) {
        if (config.TOGGLED && Minecraft.getMinecraft().thePlayer.moveForward > 0) {
            Minecraft.getMinecraft().thePlayer.setSprinting(true);
        }
    }

    @EventListener
    public void onRender(RenderOverlayEvent e) {
        if (e.getType() != RenderOverlayEvent.Type.POST || !config.DISPLAY_STATUS
                || !config.TOGGLED) {
            return;
        }

        FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;
        String text = "Sprinting [TOGGLED]";
        if (config.CHROMA_DISPLAY) {
            FontUtils
                    .drawChromaString(1, e.getScaledResolution().getScaledHeight() - fr.FONT_HEIGHT, text,
                            config.DISPLAY_DROP_SHADOW);
        } else {
            fr.drawString(text, 1, e.getScaledResolution().getScaledHeight() - fr.FONT_HEIGHT,
                    Color.WHITE.getRGB(), config.DISPLAY_DROP_SHADOW);
        }
    }

    public ToggleSprintConfig getConfig() {
        return config;
    }
}
