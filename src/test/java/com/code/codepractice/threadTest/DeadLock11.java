package com.code.codepractice.threadTest;

import org.junit.Test;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: dongxin
 * @Date: 2019/4/29 13:48
 **/
public class DeadLock11 {
    @Test
    public void test() throws InterruptedException {
        Processor11 processor = new Processor11();
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
    }

    /**
     * 模拟简单的死锁
     * t1 hold lock1 wait for lock2
     * t2 hold lock2 wait for lock1
     */
    @Test
    public void  simpleDeadLock(){
        Object lock1=new Object();
        Object lock2=new Object();
        Thread thread1=new Thread(()->{
            synchronized (lock1){
                System.out.println("thread 1 hold lock1");
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("thread 1 wait for lock2");
                synchronized (lock2){
                    System.out.println("thread 1 holding lock1 & lock2");
                }
            }
        });

        Thread thread2=new Thread(()->{
            synchronized (lock2){
                System.out.println("thread 2 hold lock2");
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("thread 2 wait for lock1");
                synchronized (lock1){
                    System.out.println("thread 2 holding lock1 & lock2");
                }
            }
        });

        thread1.start();
        thread2.start();

        ThreadUtils.delayEnd(30000);

       /* 运行结果
        thread 1 hold lock1
        thread 2 hold lock2
        thread 2 wait for lock1
        thread 1 wait for lock2
        */

    }

}

/**
 * 账户类
 */
class Account {
    private int balance = 10000;//余额

    //存入
    private void deposit(int amount) {
        balance += amount;
    }

    //转出
    private void withdraw(int amount) {
        balance += amount;
    }

    public int getBalance() {
        return balance;
    }


    //转账 account1 to account2
    public static void transfer(Account account1, Account account2, int amount) {
        account1.withdraw(amount);
        account2.deposit(amount);
    }
}

class Processor11 {
    private Account account1 = new Account();
    private Account account2 = new Account();
    private Lock lock1 = new ReentrantLock();
    private Lock lock2 = new ReentrantLock();

    private void getLocks(Lock firstLock, Lock secondLock) throws InterruptedException {
        while (true) {
            boolean gotFirst = false;
            boolean gotSecond = false;

            try {
                gotFirst = firstLock.tryLock();
                gotFirst = secondLock.tryLock();
            } finally {
                if (gotFirst && gotSecond) return;//尝试拿到两个账户的锁
                else if (gotFirst) firstLock.unlock();
                else if (gotSecond) secondLock.unlock();
            }
            Thread.sleep(1);
        }
    }

    public void firstThread() throws InterruptedException {
        Random random = new Random();
        for (int i = 0; i < 10000; i++) {
            getLocks(lock1, lock2);
            try {
                Account.transfer(account1, account2, random.nextInt(100));
            } finally {
                lock1.unlock();
                lock2.unlock();
            }
        }
    }

    public void secondThread() throws InterruptedException {
        Random random = new Random();
        for (int i = 0; i < 10000; i++) {
            getLocks(lock2, lock1);
            try {
                Account.transfer(account2, account1, random.nextInt(100));
            } finally {
                lock1.unlock();
                lock2.unlock();
            }
        }
    }

    public void finished() {
        System.out.println("account 1 balance:" + account1.getBalance());
        System.out.println("account 2 balance:" + account2.getBalance());
        System.out.println("total balance:" + (account1.getBalance() + account2.getBalance()));
    }
}
