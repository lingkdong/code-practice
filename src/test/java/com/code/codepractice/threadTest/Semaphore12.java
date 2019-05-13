package com.code.codepractice.threadTest;

import org.junit.Test;

import java.util.concurrent.*;

/**
 * @Author: dongxin
 * @Date: 2019/4/29 17:44
 * {@link java.util.concurrent.Semaphore}
 **/
public class Semaphore12 {
    @Test
    public void test() throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 7; i++) {
            executorService.submit(() -> {
                Connection12.getInstance().connect();
            });
        }
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.DAYS);//等待结束
      /* 运行结果
      pool-1-thread-1: connections ,availablePermits=2
       pool-1-thread-1: working
       pool-1-thread-3: connections ,availablePermits=1
       pool-1-thread-3: working
       pool-1-thread-4: connections ,availablePermits=0
       pool-1-thread-4: working
       pool-1-thread-5: connections ,availablePermits=2
       pool-1-thread-2: connections ,availablePermits=0
       pool-1-thread-7: connections ,availablePermits=1
       pool-1-thread-2: working
       pool-1-thread-5: working
       pool-1-thread-7: working
       pool-1-thread-6: connections ,availablePermits=1
       pool-1-thread-6: working
       */
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
    private Semaphore semaphore = new Semaphore(3, true);

    public void connect() {
        try {
            semaphore.acquire();//获取许可

            System.out.println(String.format("%s: connections ,availablePermits=%s ",
                    Thread.currentThread().getName(), semaphore.availablePermits()));//availablePermits 信号量中可用许可数
            //fk job
            System.out.println(String.format("%s: working", Thread.currentThread().getName()));
            Thread.sleep(2000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();//释放
        }
    }
}