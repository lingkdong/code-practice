package com.code.codepractice.algorithm;

import java.util.Stack;

/**
 * @Author: dongxin
 * @Date: 2019/11/15 17:51
 * Question4:用另一个堆栈对当前堆栈进行排序
 **/
public class SortStackByAnother4 {
    private Stack<Integer> a;
    private Stack<Integer> b;

    public SortStackByAnother4(Integer... value) {
        this.a = new Stack<>();
        this.b = new Stack();
        for (Integer item : value) {
            a.add(item);
        }
    }


}
