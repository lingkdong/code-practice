package com.code.codepractice.threadTest;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @Author: dongxin
 * @Date: 2019/4/29 17:44
 * {@link java.util.concurrent.Semaphore}
 **/
public class Semaphore12 {
   @Test
    public void test() throws InterruptedException {
       ExecutorService executorService= Executors.newCachedThreadPool();
       for(int i=0;i<20;i++){
           executorService.submit(()->Connection12.getInstance().connect());
       }
       executorService.shutdown();
       executorService.awaitTermination(1, TimeUnit.DAYS);//等待结束
   }
}

class Connection12 {
    private static Connection12 instance = new Connection12();

    private Connection12() {
    }

    public static Connection12 getInstance() {
        return instance;
    }

    //fair:公平
    private Semaphore semaphore = new Semaphore(10, true);

    public void connect() {
        try {
            semaphore.acquire();

            System.out.println(String.format("%s: current  connections (max 10 allowed),%s ",
                    Thread.currentThread().getName(), semaphore.availablePermits()));
            //fk job
            System.out.println(String.format("%s: working", Thread.currentThread().getName()));
            Thread.sleep(2000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
        }
    }
}