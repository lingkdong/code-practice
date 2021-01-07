package com.code.codepractice.algorithm.stack;

/**
 * @Author: dongxin
 * @Date: 2019/11/15 14:40
 * Question1:使用数据实现堆栈
 * push,pop,peek
 * https://www.cs.usfca.edu/~galles/visualization/StackArray.html
 **/
public class StackByArray1 {
    private int[] value;
    private int top;//top 指针
    private int size;

    public StackByArray1(int size) {
        this.size = size;
        value = new int[size];
        top = -1;//初始化时 top=-1;
    }

    private boolean isFull() {
        return top == size - 1;//满了
    }

    private boolean isEmpty() {
        return top == -1;
    }

    public void push(int data) {
        if (isFull()) {
            throw new IllegalArgumentException("stack is full");
        }
        top++;
        value[top] = data;
        System.out.println("push data :" + data);
    }

    public int pop() {
        if (isEmpty()) {
            throw new IllegalArgumentException("statck is empty");
        }
        int data = value[top];
        top--;
        System.out.println("pop data:" + data);
        return data;
    }

    public int peek() {
        if (isEmpty()) {
            throw new IllegalArgumentException("statck is empty");
        }
        int data = value[top];
        System.out.println("peek data:" + data);
        return data;
    }
}
