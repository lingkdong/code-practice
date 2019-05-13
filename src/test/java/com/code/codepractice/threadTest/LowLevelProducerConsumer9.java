package com.code.codepractice.threadTest;

import org.junit.Test;

import java.util.LinkedList;

/**
 * @Author: dongxin
 * @Date: 2019/4/28 18:46
 **/
public class LowLevelProducerConsumer9 {
    @Test
    public void test() {
        final Processor9 processor = new Processor9();
        Thread thread1 = new Thread(() -> {
            try {
                processor.produce();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread thread2 = new Thread(() -> {
            try {
                processor.consume();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        thread1.start();
        thread2.start();

        ThreadUtils.delayEnd(3000);
        System.exit(0);
        /*某次运行结果
        list  add value=0,listSize=1
        list  add value=1,listSize=2
        list  add value=2,listSize=3
        list  add value=3,listSize=4
        list  add value=4,listSize=5
        list is full cannot add
        list remove value=0,list size=4
        list  add value=5,listSize=5
        list is full cannot add
        list remove value=1,list size=4
        list  add value=6,listSize=5
        list is full cannot add
        list remove value=2,list size=4
        list  add value=7,listSize=5
        list is full cannot add
        list remove value=3,list size=4
        list  add value=8,listSize=5
        list is full cannot add
        *
         */
    }
}

class Processor9 {
    private LinkedList<Integer> list = new LinkedList<>();
    private final int limit =5;
    private final Object lock = new Object();//lock

    public void produce() throws InterruptedException {
        int value = 0;
        while (true) {
            synchronized (lock) {
                if (list.size() == limit) {
                    System.out.println("list is full cannot add");
                    lock.wait();//等待
                }
                list.add(value);
                System.out.println("list  add value=" + value + ",listSize=" + list.size());
                value++;
                lock.notify();
            }
        }
    }


    public void consume() throws InterruptedException {
        while (true) {
            synchronized (lock) {
                if (list.size() == 0) {
                    System.out.println("list is empty.cannot remove");
                    lock.wait();//等待
                }

                int item = list.removeFirst();
                System.out.println("list remove value=" + item + ",list size=" + list.size());
                lock.notify();
            }
            Thread.sleep(1000);
        }
    }

}
