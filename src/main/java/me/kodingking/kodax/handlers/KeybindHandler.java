package me.kodingking.kodax.handlers;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import me.kodingking.kodax.Kodax;
import me.kodingking.kodax.keybind.KeybindConfig;
import me.kodingking.kodax.keybind.XenonKeybind;
import me.kodingking.kodax.keybind.impl.ToggleSprintKeybind;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;

public class KeybindHandler {

  @Retention(RetentionPolicy.RUNTIME)
  public @interface BindRegister {

  }

  private List<XenonKeybind> xenonKeybinds = new ArrayList<>();
  public KeybindConfig keybindConfig = new KeybindConfig();

  @BindRegister
  public ToggleSprintKeybind toggleSprintKeybind = new ToggleSprintKeybind();

  public void loadBinds() {
    Kodax.CONFIG.register(keybindConfig);

    for (Field f : getClass().getDeclaredFields()) {
      if (f.getDeclaredAnnotation(BindRegister.class) != null) {
        try {
          f.setAccessible(true);
          XenonKeybind keybind = (XenonKeybind) f.get(this);

          xenonKeybinds.add(keybind);

          List<KeyBinding> newBinds = new ArrayList<>(
              Arrays.asList(Minecraft.getMinecraft().gameSettings.keyBindings));
          newBinds.add(keybind);
          Minecraft.getMinecraft().gameSettings.keyBindings = newBinds.toArray(new KeyBinding[0]);
        } catch (IllegalAccessException e) {
          e.printStackTrace();
        }
      }
    }
  }

  public void loadPrevBinds() {
    for (XenonKeybind keybind : xenonKeybinds) {
      if (keybindConfig.KEY_VALUES.containsKey(keybind.getKeyDescription())) {
        keybind.setKeyCodeNoConfig(
            Math.toIntExact(
                Math.round(keybindConfig.KEY_VALUES.get(keybind.getKeyDescription()))));
      }
    }
  }

  public List<XenonKeybind> getXenonKeybinds() {
    return xenonKeybinds;
  }

}
