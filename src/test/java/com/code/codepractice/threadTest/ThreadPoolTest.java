package com.code.codepractice.threadTest;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: dongxin
 * @Date: 2019/4/11 17:02
 * 线程池
 **/
public class ThreadPoolTest {
    @Test
    public void test(){
        ExecutorService executor= Executors.newFixedThreadPool(2);//固定大小线程池

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