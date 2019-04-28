package com.code.codepractice.threadTest;

import org.junit.Test;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @Author: dongxin
 * @Date: 2019/4/26 17:50
 * produce-consumer pattern in java using {@link java.util.concurrent.ArrayBlockingQueue}
 **/
public class ProducerConsumer7 {

    private BlockingQueue queue=new ArrayBlockingQueue(10);


    private void producer(){
        Random random=new Random();
        while (true){
            Integer item=random.nextInt(100);
            queue.add(item);//if queue full 10 then wait
            System.out.println("queue add element:"+item);
        }
    }
    private void consumer() throws InterruptedException {
        Random random=new Random();
        while (true){
            Thread.sleep(100);
            if(random.nextInt(10)==0){
                System.out.println("take queue value="+queue.take());
            }
        }
    }

    @Test
    public void test(){
        Thread thread1=new Thread(()-> producer());
        Thread thread2=new Thread(()-> {
            try {
                consumer();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread1.start();
        thread2.start();

        ThreadUtils.delayEnd(30000);
        System.exit(0);//强制退出
    }
}
