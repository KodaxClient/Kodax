package me.kodingking.kodax.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class Multithreading {

    private static AtomicInteger INC_INT = new AtomicInteger();
    private static ExecutorService EXECUTOR = Executors.newFixedThreadPool(100, r -> new Thread(r, "Multithreading-" + INC_INT.incrementAndGet()));

    public static void run(Runnable runnable) {
        EXECUTOR.execute(runnable);
    }

}
