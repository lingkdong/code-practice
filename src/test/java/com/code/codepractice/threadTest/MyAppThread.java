package com.code.codepractice.threadTest;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 自定Thread
 */
public class MyAppThread extends Thread {
    public static final String DEFAULT_NAME = "MyAppThread";
    private static volatile boolean defaultLifecycle = false;//是否是debug环境
    private static final AtomicInteger created = new AtomicInteger();
    private static final AtomicInteger alive = new AtomicInteger();
    private static final Logger logger = Logger.getAnonymousLogger();

    public MyAppThread(Runnable target, String name) {
        super(target, name + "-" + created.incrementAndGet());
        setUncaughtExceptionHandler((t, e) -> logger.log(Level.SEVERE, "UNCAUGHT in thread " + t.getName(), e));
    }

    public MyAppThread(Runnable target) {
        this(target, DEFAULT_NAME);
    }

    public void run() {
        boolean debug = defaultLifecycle;
        if (debug) logger.log(Level.FINE, "Created " + getName());
        try {
            alive.incrementAndGet();
            super.run();
        } finally {
            alive.decrementAndGet();
            if (debug) logger.log(Level.FINE, "Exiting " + getName());
        }
    }

    public static int getThreadCreated() {
        return created.get();
    }

    public static int getThreadAlive() {
        return alive.get();
    }

    public static boolean isDefaultLifecycle() {
        return defaultLifecycle;
    }

    public static void setDefaultLifecycle(boolean defaultLifecycle) {
        MyAppThread.defaultLifecycle = defaultLifecycle;
    }
}
