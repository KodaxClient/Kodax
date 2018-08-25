package me.kodingking.kodax.mods.keystrokes;

import me.kodingking.kodax.Kodax;
import me.kodingking.kodax.event.EventBus;
import me.kodingking.kodax.mods.core.AbstractCoreMod;

@AbstractCoreMod.Meta(name = "Keystrokes", version = "0.1", authors = {"KodingKing"})
public class KeystrokesMod extends AbstractCoreMod {

    private KeystrokesConfig config;

    @Override
    public void onLoad() {
        EventBus.register(new KeystrokesListener(this));
        Kodax.CONFIG.register(config = new KeystrokesConfig());
    }

    @Override
    public void onClose() {

    }

    public KeystrokesConfig getConfig() {
        return config;
    }
}
