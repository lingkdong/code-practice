package com.code.codepractice.threadTest;

import org.junit.Test;

import static com.code.codepractice.threadTest.ThreadUtils.*;


/**
 * @Author: dongxin
 * @Date: 2019/4/10 16:06
 * 创建线程有两种方式
 * {@link  java.lang.Runnable}
 * {@link  java.lang.Thread}//thread 也是 implement Runnable
 **/
public class StartThread1 {

    @Test
    public void test() throws InterruptedException {
        Thread thread1=new ThreadByExtends();
        thread1.start();

        Thread thread2=new ThreadByExtends();
        thread2.start();

        Thread thread3=new Thread(new ThreadByExtends());
        thread3.start();

        Thread thread4=new Thread(new ThreadByExtends());
        thread4.start();
        // 延迟 test 结束
        delayEnd();
    }

    @Test
    public void test2(){
        // anonymous 匿名实现
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<7;i++){
                    System.out.println("hello "+i+ " thread :"+Thread.currentThread().getName());
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();

        // 延迟 test 结束
        delayEnd();
    }
}
//1.通过extends Thread
class ThreadByExtends extends Thread{
    @Override
    public void run() {
      for(int i=0;i<5;i++){
          System.out.println("hello "+i+ " thread :"+Thread.currentThread().getName());
          try {
              Thread.sleep(100);
          } catch (InterruptedException e) {
              e.printStackTrace();
          }
      }
    }
}
//2.通过 实现 Runnable
class ThreadByImplement implements Runnable{

    @Override
    public void run() {
        for(int i=0;i<6;i++){
            System.out.println("hello "+i+ " thread :"+Thread.currentThread().getName());
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

