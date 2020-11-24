package com.code.codepractice.threadTest;

import java.util.concurrent.ThreadFactory;

/**
 * 自定义线程工厂
 */
public class MyThreadFactory implements ThreadFactory {
    private String poolName;

    @Override
    public Thread newThread(Runnable r) {
        return new MyAppThread(r, poolName);
    }

    public MyThreadFactory(String poolName) {
        this.poolName = poolName;
    }

}
