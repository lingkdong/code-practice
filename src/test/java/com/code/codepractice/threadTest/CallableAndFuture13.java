package com.code.codepractice.threadTest;

import org.junit.Test;

import java.util.Random;
import java.util.concurrent.*;

import static com.code.codepractice.threadTest.ThreadUtils.delayEnd;

/**
 * @Author: dongxin
 * @Date: 2019/4/10 14:37
 * {@link java.util.concurrent.Callable} and
 * {@link java.util.concurrent.Future}
 * in Java to get results from your threads and to allow
 * your threads to throw exceptions. Plus, Future allows you to control your
 * threads, checking to see if they’re running or not, waiting for results and
 * even interrupting them or de-scheduling them.
 * 获取线程执行结果 或 获取 Exceprion
 * future 允许你控制线程是否执行 或等待结果 甚至中断或取消任务
 **/
public class CallableAndFuture13 {
    /**
     * 使用场景  有个任务交给线程去异步执行， 不知道最终执行结果
     * 可以使用 callable 和 future 跟踪执行结果 回写状态数据
     */
    @Test
    public void test(){
        ExecutorService executor= Executors.newSingleThreadExecutor();

        //anonymous callable
        Future future=executor.submit((Callable<Object>) () -> {//捕捉你需要的异常
             Random random=new Random();
             Integer duration=random.nextInt(5000);
             if(duration>2000){
                 throw new TimeoutException("sleep too long");//抛出的异常
             }
            System.out.println("sleep start");
            try {
                Thread.sleep(duration);
            } catch (InterruptedException e) {
            }
            System.out.println("sleep end");
            return duration;
        });

        executor.shutdown();
        try {
            //future.get() Waits if necessary for the computation to complete, and then retrieves its result
            System.out.println(future.get());//等待完成获取 结果
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        //延迟 test结束
        delayEnd();

    }

}
