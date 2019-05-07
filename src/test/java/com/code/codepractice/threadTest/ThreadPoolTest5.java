package com.code.codepractice.threadTest;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Author: dongxin
 * @Date: 2019/4/11 17:02
 * 线程池
 **/
public class ThreadPoolTest5 {
    private static final int size=7;
    @Test
    public void test(){
        ExecutorService executor= Executors.newFixedThreadPool(size);//固定大小线程池
        for (int i=0;i<size;i++){
          executor.submit(new Process(i));
        }
        executor.shutdown();
        System.out.println("all tasked submited");

        try {
            executor.awaitTermination(1, TimeUnit.DAYS);//the maximum time to wait
        } catch (InterruptedException ignored) {
        }
        System.out.println("All tasks completed.");

    }
}

class Process implements Runnable{
    private int id;

    public Process(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        System.out.println("start "+id);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("end "+id);
    }
}