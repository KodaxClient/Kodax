package me.kodingking.kodax.mods.autotip;

import me.kodingking.kodax.Kodax;
import me.kodingking.kodax.mods.autotip.config.AutoTipConfig;
import me.kodingking.kodax.mods.core.AbstractCoreMod;

@AbstractCoreMod.Meta(name = "Auto Tip", version = "0.1", authors = {"KodingKing"})
public class AutoTipMod extends AbstractCoreMod {

  private AutoTipConfig config;

  @Override
  public void onLoad() {
    Kodax.CONFIG.register(config = new AutoTipConfig());
    Kodax.EVENT_BUS.register(new AutoTipListener());
  }

  @Override
  public void onClose() {

  }

  public AutoTipConfig getConfig() {
    return config;
  }
}
