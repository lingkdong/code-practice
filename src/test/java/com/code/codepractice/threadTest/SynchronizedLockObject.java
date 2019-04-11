package com.code.codepractice.threadTest;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 同步代码块 synchronized lockObject
 * @Author: dongxin
 * @Date: 2019/4/11 14:17
 * 定义两个不同对象锁，可以交替执行 一个线程执行 stageOne 其他线程可执行 stageTwo
 * 同一对象 两个同步方法不可交替执行，一次只能执行一个{@link com.code.codepractice.threadTest.SynchronizedLockMethod}
 **/
public class SynchronizedLockObject {

    private Random random = new Random();
    //定义两个共享变量
    private List<Integer> list1 = new ArrayList<>();
    private List<Integer> list2 = new ArrayList<>();
    //定义两个锁对象
    private Object lockObject1 = new Object();
    private Object lockObject2 = new Object();

    //定义两个不同对象锁，可以交替执行 一个线程执行 stageOne 其他线程可执行 stageTwo
    //同一对象 两个同步方法不可交替执行，一次只能执行一个
    public void stageOne() {
        //同步代码块
        synchronized (lockObject1) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            list1.add(random.nextInt(100));
        }
    }


    public void stageTwo() {
        //synchronized 块
        synchronized (lockObject2) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            list2.add(random.nextInt(100));
        }

    }

    public void process() {
        for (int i = 0; i < 1000; i++) {
            stageOne();
            stageTwo();
        }
    }

    @Test
    public void test() {
        long startTime = System.currentTimeMillis();

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                process();
            }
        });
        thread1.start();

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                process();
            }
        });
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("time taken:" + (System.currentTimeMillis() - startTime));
        System.out.println("list1 size=" + list1.size() + " list2 size=" + list2.size());
    }
    //结果
    //time taken:2058 millis
    //list1 size=2000 list2 size=2000

    ///定义两个不同锁，一个线程执行 stageOne 其他线程可执行 stageTwo
    //because time <list1+list2（程序中已设定 list add 执行一次 sleep 1 millis）


}
