package com.code.codepractice.algorithm.queue;

/**
 * @Author: dongxin
 * @Date: 2019/11/18 16:57
 * Question6:使用链表实现队列；
 * add ,remove,peek
 **/
public class QueueByLinkList6 {
    private Node first;//首部，首部先移除
    private Node last;//尾部，数据会追加到尾部

    private class Node {
        private int value;
        private Node next;

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }
    }

    //add 时尾部添加
    public void add(int data) {
        Node newLast = new Node();
        newLast.setValue(data);
        if (first == null) {
            first = newLast;
            last = newLast;
        } else {
            Node historyLast = last;
            last = newLast;
            historyLast.next = last;
        }
        System.out.println("queue add data:" + data);
    }

    //remove 时 头部减少
    public int remove() {
        if (first == null) {
            throw new IllegalArgumentException("queue is empty");
        }
        Node historyFirst = first;
        first = first.next;
        int data = historyFirst.getValue();
        System.out.println("queue remove data:" + data);
        return data;
    }

    public int peek() {
        if (first == null) {
            throw new IllegalArgumentException("queue is empty");
        }
        int data = first.getValue();
        System.out.println("queue peek data:" + data);
        return data;
    }
}
