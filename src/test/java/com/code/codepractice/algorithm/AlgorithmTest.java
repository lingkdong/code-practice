package com.code.codepractice.algorithm;

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
}
