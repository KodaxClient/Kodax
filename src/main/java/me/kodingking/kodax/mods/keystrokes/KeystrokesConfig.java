package me.kodingking.kodax.mods.keystrokes;

import me.kodingking.kodax.config.SaveVal;

public class KeystrokesConfig {

  @SaveVal
  public boolean ENABLED = true;
  @SaveVal
  public int SCALE = 100;
  @SaveVal
  public int FADE_INCREMENT = 3;
  @SaveVal
  public int SPACING = 5;
  @SaveVal
  public int CORNER = 1;
  //Chroma, RGB
  @SaveVal
  public String MODE = "CHROMA";
  @SaveVal
  public boolean DROP_SHADOW = false;
  @SaveVal
  public boolean DISPLAY_SPACE = true;
  @SaveVal
  public boolean DISPLAY_MOUSE_BUTTONS = false;
  @SaveVal
  public boolean DISPLAY_CPS = false;

  @SaveVal
  public int COLOR_R = 255;
  @SaveVal
  public int COLOR_G = 255;
  @SaveVal
  public int COLOR_B = 255;

  public void cycleMode() {
    if (MODE.equalsIgnoreCase("CHROMA")) {
      MODE = "RGB";
    } else if (MODE.equalsIgnoreCase("RGB")) {
      MODE = "CHROMA";
    } else {
      MODE = "CHROMA";
    }
  }

  public String getCorner() {
    switch (CORNER) {
      case 1:
        return "Top Left";
      case 2:
        return "Top Right";
      case 3:
        return "Bottom Left";
      case 4:
        return "Bottom Right";
    }
    return "";
  }
}
