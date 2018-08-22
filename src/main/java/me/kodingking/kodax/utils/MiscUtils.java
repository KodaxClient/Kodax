package me.kodingking.kodax.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.lwjgl.input.Mouse;

public class MiscUtils {

  private static List<Long> leftCps = new ArrayList<>();
  private static boolean lastClick = false;

  public static void update() {
    boolean m = Mouse.isButtonDown(0);
    if (m != lastClick) {
      lastClick = m;
      if (m) {
        leftCps.add(System.currentTimeMillis());
      }
    }
  }

  public static int getLeftCps() {
    Iterator<Long> iterator = leftCps.iterator();
    while (iterator.hasNext()) {
      if (System.currentTimeMillis() - iterator.next() > 1000L) {
        iterator.remove();
      }
    }
    return leftCps.size();
  }

  public static String insertDashUUID(String uuid) {
    return uuid.replaceFirst(
        "(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}+)", "$1-$2-$3-$4-$5"
    );
  }

}
