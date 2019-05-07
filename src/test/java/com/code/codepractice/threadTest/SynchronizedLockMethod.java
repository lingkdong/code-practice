package com.code.codepractice.threadTest;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 同步方法 synchronized method
 * @Author: dongxin
 * @Date: 2019/4/11 15:50
 * 同一对象的两个同步方法 不能交错进行，当一个线程执行同步方法时，其他线程调用同一个对象的同步方法会暂停执行 直到第一个线程完成
 * 另外同步方法退出时，会自动与后续调用先发生联系，已保证所有对象可以visiable state更改
 **/
public class SynchronizedLockMethod {

    private Random random = new Random();
    //定义两个共享变量
    private List<Integer> list1 = new ArrayList<>();
    private List<Integer> list2 = new ArrayList<>();

    public synchronized void stageOne() {

        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        list1.add(random.nextInt(10));

    }


    public synchronized void stageTwo() {

        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        list2.add(random.nextInt(10));

    }

    public void process() {
        for (int i = 0; i < 100; i++) {
            stageOne();
            stageTwo();
        }
    }

    @Test
    public void test() {
        System.out.println("start...");
        long startTime = System.currentTimeMillis();

        Thread thread1 = new Thread(() -> process());
        thread1.start();

        Thread thread2 = new Thread(() -> process());
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("time taken:" + (System.currentTimeMillis() - startTime));
        System.out.println("list1 size=" + list1.size() + " list2 size=" + list2.size());
        System.out.println("end...");
    }
//    结果
//    start...
//    time taken:478
//    list1 size=200 list2 size=200
//    end...

//    time>list1+list2 说明没交错执行 （（程序中已设定 list add 执行一次 sleep 1 millis）
}
