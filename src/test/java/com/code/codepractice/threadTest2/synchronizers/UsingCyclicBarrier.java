package com.code.codepractice.threadTest2.synchronizers;

import org.junit.Test;

import java.util.Random;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Author: dongxin
 * @Date: 2019/5/14 18:36
 * {@link java.util.concurrent.CyclicBarrier}
 * A synchronization aid that allows a set of threads to all wait for
 *    each other to reach a common barrier point.  CyclicBarriers are
 *    useful in programs involving a fixed sized party of threads that
 *    must occasionally wait for each other. The barrier is called
 *    <em>cyclic</em> because it can be re-used after the waiting threads
 *    are released.
 *    同步辅助类 允许一组线程 在公共的屏障点等待，CyclicBarriers 在固定大小线程中非常有用
 *    循环屏障可以在线程释放后重用
 *
 **/
public class UsingCyclicBarrier {
    @Test
    // todo not finished
    public void test() throws InterruptedException {
        int parties=10;
        Runnable barrierAction= () -> System.out.println("well done guys.");
        CyclicBarrier cyclicBarrier=new CyclicBarrier(parties,barrierAction);
        Random random=new Random();
        Runnable task= () -> {
            //sleep
            try {
                System.out.println("doing task for "+Thread.currentThread().getName());
                Thread.sleep(random.nextInt(1000));
                System.out.println("done for "+Thread.currentThread().getName());
                cyclicBarrier.wait();//等待
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        };
        ExecutorService executorService= Executors.newCachedThreadPool();
        for (int i = 0; i < parties; i++) {
            executorService.execute(task);
        }
       executorService.shutdown();

        executorService.awaitTermination(1, TimeUnit.DAYS);
    }
}
