package com.code.codepractice.algorithm.queue;

/**
 * @Author: dongxin
 * @Date: 2019/11/18 11:03
 * Question5:用数组实现队列
 * queue:队列先进先出 ，
 * add remove peek
 * https://www.cs.usfca.edu/~galles/visualization/QueueLL.html
 **/
public class QueueByArray5 {
    private int[] value;//存放值
    private int size;
    private int pointer;//添加时的指针，先进先出 指针小的先出，
    private int removePointer;//移除时的指针

    public QueueByArray5(int size) {
        this.size = size;
        value = new int[size];
        pointer = -1;
        removePointer=size-1;
    }

    private boolean isFull() {
        return pointer == size - 1;
    }

    private boolean isEmpty() {
        return removePointer == size-1;
    }

    public void add(int data) {
        if (isFull()) {
            throw new IllegalArgumentException("queue is full");
        }
        pointer++;
        removePointer--;
        value[pointer] = data;
        System.out.println("queue add data:" + data);
    }

    public int remove() {
        if (isEmpty()) {
            throw new IllegalArgumentException("queue is empty");
        }
        //添加的指针和出对列的指针不一样
        int data = value[removePointer];
        removePointer++;
        pointer--;
        System.out.println("queue remove data:" + data);
        return data;
    }

    public int peek() {
        if (isEmpty()) {
            throw new IllegalArgumentException("queue is empty");
        }
        int data = value[pointer];
        System.out.println("queue peek data:" + data);
        return data;
    }
}
