package me.kodingking.kodax.utils;

import com.darkmagician6.eventapi.EventTarget;
import java.util.ArrayList;
import java.util.List;
import me.kodingking.kodax.events.UpdateEvent;
import me.kodingking.kodax.events.world.WorldChangeEvent;
import net.minecraft.client.Minecraft;

public class Scheduler {

  private List<Task> tasks = new ArrayList<>();

  public void schedule(Runnable task, long tickDelay) {
    long currentWorldTicks = Minecraft.getMinecraft().theWorld.getTotalWorldTime();
    tasks.add(new Task(currentWorldTicks + tickDelay, task));
  }

  @EventTarget
  public void onTick(UpdateEvent e) {
    long currentWorldTicks = Minecraft.getMinecraft().theWorld.getTotalWorldTime();
    tasks.removeIf(task -> {
      boolean shouldRemove = task.getTargetTicks() >= currentWorldTicks;
      if (shouldRemove) {
        task.run();
      }
      return shouldRemove;
    });
  }

  @EventTarget
  public void onWorldChange(WorldChangeEvent e) {
    if (e.getType() != WorldChangeEvent.Type.PRE) {
      return;
    }
    tasks.clear();
  }

  public class Task {

    private long targetTicks;
    private Runnable task;

    public Task(long targetTicks, Runnable task) {
      this.targetTicks = targetTicks;
      this.task = task;
    }

    public long getTargetTicks() {
      return targetTicks;
    }

    public void run() {
      task.run();
    }
  }

}
