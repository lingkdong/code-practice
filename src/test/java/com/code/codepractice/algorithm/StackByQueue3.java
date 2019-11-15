package com.code.codepractice.algorithm;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @Author: dongxin
 * @Date: 2019/11/15 17:14
 * Question3:使用两个队列实现堆栈；
 * push pop peek
 **/
public class StackByQueue3 {
    private Queue<Integer> a;//存储
    private Queue<Integer> b;//永远只存储最新的一个

    public StackByQueue3() {
        this.a = new LinkedList<>();
        this.b = new LinkedList<>();
    }

    public void push(int data) {
        if (b.isEmpty()) {
            b.add(data);
        } else {
            Integer historyNew = b.remove();
            b.add(data);//最新数据
            a.add(historyNew);//historyNew保存在a中
        }
        System.out.println("push data:" + data);
    }

    public int pop() {
        if (b.isEmpty()) {
            throw new IllegalArgumentException("stack is empty");
        }
        Integer data = b.remove();//需要弹出的数据；b现在为空
        //a中数据存入B
        while (!a.isEmpty()) {
            b.add(a.remove());
        }
        //b 中保留一个 其余的放入A;
        while (!b.isEmpty() && b.size() > 1) {
            a.add(b.remove());
        }
        System.out.println("pop data:" + data);
        return data;
    }

    public int peek() {
        if (b.isEmpty()) {
            throw new IllegalArgumentException("stack is empty");
        }
        Integer data = b.peek();
        System.out.println("peek data:" + data);
        return data;
    }
}
