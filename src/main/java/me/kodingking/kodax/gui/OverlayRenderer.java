package me.kodingking.kodax.gui;

import com.darkmagician6.eventapi.EventTarget;
import java.awt.Color;
import me.kodingking.kodax.Kodax;
import me.kodingking.kodax.events.UpdateEvent;
import me.kodingking.kodax.events.render.RenderOverlayEvent;
import me.kodingking.kodax.object.Notification;
import me.kodingking.kodax.utils.FontUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;

public class OverlayRenderer {

  private int height = 40;
  private int width = 225;
  private int speed = 6;
  private int length = 4;

  @EventTarget
  public void onRenderOverlay(RenderOverlayEvent e) {
    if (e.getType() != RenderOverlayEvent.Type.POST) {
      return;
    }

    renderNotifications(e.getScaledResolution());
  }

  @EventTarget
  public void onUpdate(UpdateEvent e) {
    Notification notification = Kodax.INSTANCE.getHandlerController().getNotificationHandler()
        .getCurrentNotification();

    if (notification.getCurrentStatus() == Notification.Status.NOT_SHOWING) {
      notification.setCurrentStatus(Notification.Status.PULLING_OUT);
    }
    switch (notification.getCurrentStatus()) {
      case PULLING_OUT:
        if (notification.getXOffset() + speed < width) {
          notification.setXOffset(notification.getXOffset() + speed);
        } else {
          notification.setCurrentStatus(Notification.Status.STATIC);
        }
        break;
      case STATIC:
        if (notification.getStaticLength() < System.currentTimeMillis()) {
          notification.setCurrentStatus(Notification.Status.PULLING_IN);
        } else if (notification.getStaticLength() == 0) {
          notification.setStaticLength(System.currentTimeMillis() + length * 1000);
        }
        break;
      case PULLING_IN:
        if (notification.getXOffset() - speed > width) {
          notification.setXOffset(notification.getXOffset() - speed);
        } else {
          notification.setCurrentStatus(Notification.Status.NOT_SHOWING);
          Kodax.INSTANCE.getHandlerController().getNotificationHandler().nextNotification();
          return;
        }
    }
  }

  private void renderNotifications(ScaledResolution sr) {
    if (!Kodax.INSTANCE.getHandlerController().getNotificationHandler()
        .hasQueuedNotification()) {
      return;
    }

    FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;

    Notification notification = Kodax.INSTANCE.getHandlerController().getNotificationHandler()
        .getCurrentNotification();

    Gui.drawRect(sr.getScaledWidth() - notification.getXOffset(),
        sr.getScaledHeight() - height + 10, sr.getScaledWidth() + width - notification.getXOffset(),
        sr.getScaledHeight() - 10, new Color(41, 41, 41).getRGB());
    FontUtils.drawChromaString(sr.getScaledWidth() + 3 - notification.getXOffset(),
        sr.getScaledHeight() - height + 12, notification.getTitle(), false);
    fr.drawString(notification.getContent(), sr.getScaledWidth() + 3 - notification.getXOffset(),
        sr.getScaledHeight() - height + 22, new Color(255, 255, 253).getRGB(), false);
  }
}
