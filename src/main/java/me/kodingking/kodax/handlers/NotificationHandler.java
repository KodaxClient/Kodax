package me.kodingking.kodax.handlers;

import java.util.ArrayList;
import java.util.List;
import me.kodingking.kodax.object.Notification;

public class NotificationHandler {

  private List<Notification> queuedNotifications = new ArrayList<>();

  public Notification getCurrentNotification() {
    if (queuedNotifications.size() == 0) {
      queuedNotifications.add(new Notification("Testing", "This is a test....."));
    }
    return queuedNotifications.get(0);
  }

  public boolean hasQueuedNotification() {
    return getCurrentNotification() != null;
  }

  public void nextNotification() {
    queuedNotifications.remove(0);
  }
}
