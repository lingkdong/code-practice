package com.code.codepractice.threadTest;

import org.junit.Test;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author:
 * @Date:
 * 使用 ReentrantLock 和lock.Condition控制线程执行
 * firstThread start后 lock.await等待
 * 执行 secondThread 执行完后唤醒 firstThread
 * firstThread继续执行
 **/
public class ReentrantLock10 {
    @Test
    public void test() throws InterruptedException {
        Processor10 processor = new Processor10();
        Thread thread1 = new Thread(() -> {
            try {
                processor.firstThread();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread thread2 = new Thread(() -> {
            try {
                processor.secondThread();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        processor.finished();
     /*   运行结果
        firstThread wait...
        secondThread runing...
        secondThread increment count value:1
        secondThread increment count value:2
        firstThread woke up!
                firstThread increment count value:3
        firstThread increment count value:4
        count is 4
     */
    }
}

class Processor10 {
    private int count = 0;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();//lock condition


    public void firstThread() throws InterruptedException {
        lock.lock();
        System.out.println("firstThread wait...");
        condition.await();// 第一个线程 等待
        System.out.println("firstThread woke up!");
        try {
            increment("firstThread");
        } finally {
            lock.unlock();
        }
    }

    public void secondThread() throws InterruptedException {
        Thread.sleep(1000);
        lock.lock();
        System.out.println("secondThread runing...");
        try {
            increment("secondThread");
            condition.signal();
        } finally {
            lock.unlock();//释放锁 让第一个线程执行
        }
    }

    public void finished() {
        System.out.println("count is " + count);
    }


    private void increment(String name) {
        for (int i = 0; i < 2; i++) {
            count++;
            System.out.println(name + " increment count value:" + count);
        }
    }
}