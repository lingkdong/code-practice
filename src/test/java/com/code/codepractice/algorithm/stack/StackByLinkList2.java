package com.code.codepractice.algorithm.stack;

/**
 * @Author: dongxin
 * @Date: 2019/11/15 15:29
 * Question2:使用链表实现堆栈
 * push,pop,peek
 **/
public class StackByLinkList2 {
    private Node head;//

    //node 类
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

    private boolean isEmpty() {
        return head == null;
    }

    public void push(int data) {
        Node newNode = new Node();
        newNode.value = data;
        if (head == null) {
            head = newNode;
        } else {
            Node historyHead = head;
            head = newNode;
            head.next = historyHead;//新节点head的next指向historyHead
        }
        System.out.println("push data:" + data);
    }

    public int pop() {
        if (isEmpty()) {
            throw new IllegalArgumentException("stack is empty");
        }
        int data = head.value;
        head = head.next;//next->head
        System.out.println("pop data:" + data);
        return data;
    }

    public int peek() {
        if (isEmpty()) {
            throw new IllegalArgumentException("stack is empty");
        }
        int data = head.value;
        System.out.println("peek data:" + data);
        return data;
    }

}
