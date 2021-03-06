package com.code.codepractice.threadTest;

import javafx.concurrent.Worker;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: dongxin
 * @Date: 2019/4/23 17:29
 * {@link java.util.concurrent.CountDownLatch}
 * A synchronization aid that allows one or more threads to wait until
   a set of operations being performed in other threads completes
   等待其他线程完成操作
    如：主线程 等待子线程完成 countDownLatch.await()
 **/
public class CountDownLatch6 {
    private int N=3;
    @Test
    public void test(){
        System.out.println("main start.");
        CountDownLatch countDownLatch=new CountDownLatch(N);
        ExecutorService executorService= Executors.newFixedThreadPool(N);//主线程
        for(int i=0;i<N;i++){
            executorService.submit(new Processor(countDownLatch));//create and start thread
        }
        executorService.shutdown();
        try {
            // Application’s main thread waits, till other service threads which are
            // as an example responsible for starting framework services have completed started all services.
            //主线程等待其他线程执行完成
            countDownLatch.await();//wait for all to finish
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("main Completed.");
        //运行结果
       /*       Started.//子线程打印
                Started.//子线程打印
                Started.//子线程打印
                Completed.
                */
    }
    @Test
    public void  test2() throws InterruptedException {
        System.out.println("main start.");
        CountDownLatch startSignal = new CountDownLatch(1);
        CountDownLatch doneSignal = new CountDownLatch(N);

        for (int i = 0; i < N; ++i) // create and start threads
            new Thread(new Worker2(startSignal, doneSignal)).start();

        System.out.println("doSomethingElse....");// don't let run yet
        startSignal.countDown();      // let all threads proceed
        System.out.println("doSomethingElse2....");// don't let run yet
        doneSignal.await();           // wait for all to finish
        System.out.println("main Completed.");
     }
}

class Processor implements Runnable{
    //子线程
    private CountDownLatch countDownLatch;

    public Processor(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        //子线程
        System.out.println("sub Thread Started.");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //Decrements the count of the latch, releasing all waiting threads if  the count reaches zero
        countDownLatch.countDown();//deal one then 减 1
    }
}


 class Worker2 implements Runnable {
     private final CountDownLatch startSignal;
     private final CountDownLatch doneSignal;

     Worker2(CountDownLatch startSignal, CountDownLatch doneSignal) {
         this.startSignal = startSignal;
         this.doneSignal = doneSignal;
     }

     public void run() {
         try {
             startSignal.await();// wait for all to finish
             System.out.println("doWork...");
             doneSignal.countDown();
         } catch (InterruptedException ex) {
         } // return;
     }
 }


