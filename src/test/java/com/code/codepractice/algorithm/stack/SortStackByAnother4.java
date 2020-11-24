package com.code.codepractice.algorithm.stack;

import java.util.Arrays;
import java.util.Stack;

/**
 * @Author: dongxin
 * @Date: 2019/11/15 17:51
 * Question4:用另一个堆栈对当前堆栈进行排序
 **/
public class SortStackByAnother4 {
    private Stack<Integer> a;//用来存储
    private Stack<Integer> b;//用来冒泡,大的冒上来

    public SortStackByAnother4(Integer... value) {
        this.a = new Stack<>();
        this.b = new Stack();
        for (Integer item : value) {
            a.add(item);//存储数据至a
        }
    }

    public void sort() {
        while (a.size() > 0) {
            Integer temp = a.pop();
            //为temp选择排序位置
            while (b.size() > 0 && temp < b.peek()) {
                a.push(b.pop());//如果比temp大塞回a中
            }
            b.push(temp);
        }
        //排序完成 b->a
        while (b.size() > 0) {
            a.push(b.pop());
        }
    }

    @Override
    public String toString() {
        return Arrays.toString(a.toArray());
    }
}
