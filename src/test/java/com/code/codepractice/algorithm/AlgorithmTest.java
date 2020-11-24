package com.code.codepractice.algorithm;

import com.code.codepractice.algorithm.queue.QueueByArray5;
import com.code.codepractice.algorithm.queue.QueueByLinkList6;
import com.code.codepractice.algorithm.stack.SortStackByAnother4;
import com.code.codepractice.algorithm.stack.StackByArray1;
import com.code.codepractice.algorithm.stack.StackByLinkList2;
import com.code.codepractice.algorithm.stack.StackByQueue3;
import org.junit.Test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

/**
 * @Author: dongxin
 * @Date: 2019/10/29 17:55
 * algorithm [ˈalgəˌriT͟Həm] 算法
 **/
public class AlgorithmTest {
    /**
     * question: 已知sqrt(2)约等于1.414，不使用数学库，将sqrt(2)精确到10位小数
     */
    @Test
    public void TestSqrt() {

    }
    @Test
    public void TestQueue(){
        BlockingQueue queue=new SynchronousQueue();
        System.out.println(queue.offer(1));
        System.out.println(queue.offer(2));
        System.out.println(queue.offer(3));
        System.out.println(queue.size());
    }


    @Test
    public void testStack(){
        StackByArray1 stack=new StackByArray1(6);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        stack.pop();
        stack.peek();
    }
    @Test
    public void testStack2(){
        StackByLinkList2 stack=new StackByLinkList2();
        stack.push(2);
        stack.push(3);
        stack.push(4);
        stack.push(5);
        stack.pop();
        stack.pop();
        stack.peek();
    }

    @Test
    public void testStack3(){
        StackByQueue3 stack=new StackByQueue3();
        stack.push(2);
        stack.push(3);
        stack.push(4);
        stack.push(5);
        stack.pop();
        stack.pop();
        stack.peek();
    }
    @Test
    public void testStack4(){
         SortStackByAnother4 stack=new SortStackByAnother4(9,11,2,3,17,1,7,9,1);
         stack.sort();
        System.out.println(stack.toString());
    }

    @Test
    public void testQueue(){
        QueueByArray5 queue=new QueueByArray5(6);
        queue.add(2);//先
        queue.add(3);
        queue.add(4);
        queue.add(5);
        queue.add(6);//后执行
        queue.remove();
        queue.remove();
        queue.peek();
    }

    @Test
    public void testQueue2(){
        QueueByLinkList6 queue=new QueueByLinkList6();
        queue.add(2);//先
        queue.add(3);
        queue.add(4);
        queue.add(5);
        queue.add(6);//后执行
        queue.remove();
        queue.remove();
        queue.peek();
        queue.peek();

    }
}
