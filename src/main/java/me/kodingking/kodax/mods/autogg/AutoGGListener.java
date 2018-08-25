package me.kodingking.kodax.mods.autogg;

import me.kodingking.kodax.Kodax;
import me.kodingking.kodax.event.EventListener;
import me.kodingking.kodax.event.events.game.GameEndEvent;
import me.kodingking.kodax.utils.ChatUtils;
import me.kodingking.kodax.utils.Multithreading;

public class AutoGGListener {

    @EventListener
    public void onGameEnd(GameEndEvent e) {
        Multithreading.run(() -> {
            try {
                Thread.sleep(
                        Kodax.INSTANCE.getHandlerController().getModHandler().getAutoGGMod().getConfig().DELAY
                                * 1000);
                ChatUtils.sendChatMessage("gg");
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        });
    }

}
