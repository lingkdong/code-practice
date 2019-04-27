package com.code.codepractice.threadTest;

import org.junit.Test;

import java.util.LinkedList;
import java.util.Queue;
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
        Scanner scanner = new Scanner(System.in);
        Thread.sleep(2000);
        synchronized (this) {
            System.out.println("wait for return key ");
            scanner.nextLine();
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
                notFull.await(); //release lock
            }
            queue.add(element);
            System.out.println("added to the queue,element=" + element);
            notEmpty.signal();//调用等待线程
        } finally {
            lock.unlock();//解锁
        }

    }

    public T take() throws InterruptedException {

        try {
            lock.lock();
            while (queue.isEmpty()) {
                System.out.println("queue is empty,can not take");
                notEmpty.await();
            }
            T item = queue.remove();
            System.out.println("queue remove element=" + item);
            notFull.signal();
            return item;
        } finally {
            lock.unlock();
        }

    }
}
 @SuppressWarnings("InfiniteLoopStatement")
class BlockingQueueApp{
    @Test
    public void test(){

    }
}


