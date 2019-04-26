package com.code.codepractice.threadTest;

import org.junit.Test;

import static com.code.codepractice.threadTest.ThreadUtils.delayEnd;


/**
 * @Author: dongxin
 * @Date: 2019/4/10 17:35
 * Volatile Keyword, <em>“… the volatile modifier guarantees that any thread that
 * reads a field will see the most recently written value  - Josh Bloch(Java 集合框架创办人)
 * https://en.wikipedia.org/wiki/Joshua_Bloch
 **/
public class VolatileKeyword2 {

    class Processor extends Thread{
        //volatile 具有可见性 但不具备原子性
        //https://www.ibm.com/developerworks/cn/java/j-jtp06197.html
        private volatile boolean running=true;
        @Override
        public void run() {
            while (running){
                System.out.println("running..");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public void shutdown(){
            running=false;
            System.out.println("shutdown..");
        }
    }

    @Test
    public void test(){
        Processor processor=new Processor();
        processor.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        processor.shutdown();

        // 延迟 test 结束
        delayEnd();
    }
}
