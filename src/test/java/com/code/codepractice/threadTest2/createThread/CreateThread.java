package com.code.codepractice.threadTest2.createThread;

import org.junit.Test;

/**
 * @Author: dongxin
 * @Date: 2019/5/13 9:52
 **/
public class CreateThread {
    @Test
    public void test() throws InterruptedException {
        Thread thread=new Thread(()-> System.out.println("start thread"));
        thread.start();

        Runnable task=()->{
            while (!Thread.currentThread().isInterrupted()){
                System.out.println("I am not interrupted,"+Thread.currentThread().getName());
            }
        };
         Thread thread2=new Thread(task);
         thread2.start();
         Thread.sleep(2000);
         thread2.interrupt();//中断线程
    }
}
