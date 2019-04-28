package com.code.codepractice.threadTest;

import org.junit.Test;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created with IntelliJ IDEA.
 * User: lkdong
 * Date: 19-4-27
 * Time: 下午6:12
 * To change this template use File | Settings | File Templates.
 * wait and notify 等待通知 类型线程设计
 * 一个或多个线程sleep 等待被其他线程唤醒
 */
public class WaitAndNotify8 {

    @Test
    public void testBlockQueue() throws InterruptedException {
        final BlockQueue<Integer> queue = new BlockQueue<>(10);
        final Random random = new Random();
        Thread thread1 = new Thread(() -> {
            try {
                for (int i = 0; i < 30; i++) {
                    queue.put(random.nextInt(10));
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread thread2 = new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                while (true) {
                    queue.take();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        thread1.start();
        thread2.start();
        thread1.join();//Waits for this thread to die.
        thread2.join();//Waits for this thread to die.


    }

    @Test
    public void testProcessor() throws InterruptedException {
        final Processor8 processor=new Processor8();
        Thread thread1=new Thread(()-> {
            try {
                processor.produce();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread thread2=new Thread(()->{
            try {
                processor.consume();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
    }

}

class Processor8 {
    public void produce() throws InterruptedException {
        synchronized (this) {
            System.out.println("producer thread running.");
            wait();
            System.out.println("resumed");//恢复
        }
    }

    public void consume() throws InterruptedException {
        Thread.sleep(2000);
        synchronized (this) {
            System.out.println("wait for return key ");
            System.out.println("press");
            System.out.println("return key pressed");
            notify();
            Thread.sleep(5000);
            System.out.println("consumption done");  //消费结束
        }
    }
}

class BlockQueue<T> {
    private Queue<T> queue = new LinkedList(); //队列
    private int capacity;//容量
    private Lock lock = new ReentrantLock();//Reentrant 可重复
    //https://docs.oracle.com/javase/7/docs/api/java/util/concurrent/locks/Condition.html
    private Condition notFull = lock.newCondition();
    private Condition notEmpty = lock.newCondition();

    BlockQueue(int capacity) {
        this.capacity = capacity;
    }

    public void put(T element) throws InterruptedException {
        try {
            lock.lock();  //加锁
            while (queue.size() == capacity) {
                System.out.println("queue is full cannot put");
                notFull.await(); //当前线程等待(put 线程等待)
            }
            queue.add(element);//添加 添加完以后 发信号给消费者 去take
            System.out.println("added to the queue,element=" + element);
            notEmpty.signal();//唤醒take
        } finally {
            lock.unlock();//解锁
        }

    }

    public T take() throws InterruptedException {
        try {
            lock.lock();
            while (queue.isEmpty()) {
                System.out.println("queue is empty,can not take");
                notEmpty.await();//remove 线程等待
            }
            T item = queue.remove();
            System.out.println("queue remove element=" + item);
            notFull.signal();//唤醒put
            return item;
        } finally {
            lock.unlock();
        }

    }
}



