package me.kodingking.kodax.handlers;

import me.kodingking.kodax.mods.autogg.AutoGGMod;
import me.kodingking.kodax.mods.autotip.AutoTipMod;
import me.kodingking.kodax.mods.keystrokes.KeystrokesMod;
import me.kodingking.kodax.mods.togglesprint.ToggleSprintMod;

public class ModHandler {

  private final KeystrokesMod keystrokesMod;
  private final AutoGGMod autoGGMod;
  private final AutoTipMod autoTipMod;
  private final ToggleSprintMod toggleSprintMod;

  public ModHandler() {
    this.keystrokesMod = new KeystrokesMod();
    this.autoGGMod = new AutoGGMod();
    this.autoTipMod = new AutoTipMod();
    this.toggleSprintMod = new ToggleSprintMod();
  }

  public KeystrokesMod getKeystrokesMod() {
    return keystrokesMod;
  }

  public AutoGGMod getAutoGGMod() {
    return autoGGMod;
  }

  public AutoTipMod getAutoTipMod() {
    return autoTipMod;
  }

  public ToggleSprintMod getToggleSprintMod() {
    return toggleSprintMod;
  }
}
