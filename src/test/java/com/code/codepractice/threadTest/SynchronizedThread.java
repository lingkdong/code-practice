package com.code.codepractice.threadTest;

import org.junit.Test;

import static com.code.codepractice.threadTest.ThreadUtils.delayEnd;

/**
 * @Author: dongxin
 * @Date: 2019/4/11 9:28
 * 1.同步方法
 * 2.同步代码块
 * synichronized
 * 同一对象 两个同步方法 不能交错进行，当一个线程执行同步方法时，其他线程调用同一个对象的同步方法会暂停执行 直到第一个线程完成
 * 另外同步方法退出时，会自动与后续调用先发生联系，已保证所有对象可以visiable state更改
 *  https://docs.oracle.com/javase/tutorial/essential/concurrency/syncmeth.html
 **/
public class SynchronizedThread {
    private int count=0;
   //同一对象 两个同步方法 不能交错进行，当一个线程执行同步方法时，其他线程调用同一个对象的同步方法会暂停执行 直到第一个线程完成
    //另外同步方法退出时，会自动与后续调用先发生联系，已保证所有对象可以visiable state更改
    //https://docs.oracle.com/javase/tutorial/essential/concurrency/syncmeth.html
    private  synchronized int increment(String threadName){
        count++;
        System.out.println("Thread in progress :"+threadName+ " and count="+count);
        return count;
    }

    public void doWork(){
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<30;i++){

                    increment(Thread.currentThread().getName());
                }
            }
        });
        thread.start();

        Thread thread2=new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<30;i++){

                    increment(Thread.currentThread().getName());
                }
            }
        });
        thread2.start();

       //join :waits for this thread to die
        try {
            thread.join();//等待线程1 运行结束
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void test(){
       SynchronizedThread synchronizedThread=new SynchronizedThread();
       synchronizedThread.doWork();

       //延迟test结束
        delayEnd();


    }
}
