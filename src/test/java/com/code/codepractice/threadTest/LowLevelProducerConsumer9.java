package com.code.codepractice.threadTest;

import org.junit.Test;

import java.util.LinkedList;

/**
 * @Author: dongxin
 * @Date: 2019/4/28 18:46
 **/
public class LowLevelProducerConsumer9 {
    @Test
   public void test(){
        final Processor9 processor=new Processor9();
       Thread thread1=new Thread(()-> {
           try {
               processor.produce();
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
       });
       Thread thread2=new Thread(()-> {
           try {
               processor.consume();
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
       });

     thread1.start();
     thread2.start();

     ThreadUtils.delayEnd(30000);
     System.exit(0);
   }
}

class Processor9{
    private LinkedList<Integer> list=new LinkedList<>();
    private final int limit=10;
    private final Object lock=new Object();

    public void produce() throws InterruptedException {
        int value=0;
        while (true){
            synchronized (lock){
                if(list.size()==limit){
                    System.out.println("list is full cannot add");
                    lock.wait();//等待
                }
                list.add(value);
                System.out.println("list  add value="+value+",listSize="+list.size());
                value++;
                lock.notify();
            }
        }
    }


    public void consume() throws InterruptedException {
        while (true){
            synchronized (lock){
                if(list.size()==0){
                    System.out.println("list is empty.cannot remove");
                    lock.wait();//等待
                }

                int item=list.removeFirst();
                System.out.println("list remove value="+item+",list size="+list.size());
                lock.notify();
            }
            Thread.sleep(1000);
        }
    }

}
