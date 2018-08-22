package me.kodingking.kodax.mods.autogg;

import me.kodingking.kodax.Kodax;
import me.kodingking.kodax.mods.autogg.config.AutoGGConfig;
import me.kodingking.kodax.mods.core.AbstractCoreMod;

@AbstractCoreMod.Meta(name = "Auto GG", version = "0.1", authors = {"KodingKing"})
public class AutoGGMod extends AbstractCoreMod {

  private AutoGGConfig config;

  @Override
  public void onLoad() {
    Kodax.CONFIG.register(config = new AutoGGConfig());
    Kodax.EVENT_BUS.register(new AutoGGListener());
  }

  @Override
  public void onClose() {

  }

  public AutoGGConfig getConfig() {
    return config;
  }
}
