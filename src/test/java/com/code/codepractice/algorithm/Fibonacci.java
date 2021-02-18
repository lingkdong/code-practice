package com.code.codepractice.algorithm;

import org.junit.Test;

/**
 * fibonacci number
 * 斐波那切数列
 * 1,2,3,5,8,13,21
 *
 */
public class Fibonacci {
    /**
     * 跳阶的题目
     * 也是fibonacii 函数
     * step=1 or 2
     * n个台阶有几种跳法
     * 列出前几项 递推
     * 台阶数                                           跳法
     * 1                                                  1
     * 2                                                  2
     * 3 (先1后面2个台阶 2 种跳，先2后面1个台阶有 1种跳)  3
     * 4 (先1后面3个台阶 3 种跳，先2后面2个台阶有 2种跳)  5
     * 5 (先1后面4个台阶 5 种跳，先2后面3个台阶有 3种跳)  8
     * 6 (先1后面5个台阶 8 种跳，先2后面4个台阶有 5种跳)  13
     * 7 (先1后面6个台阶 13 种跳，先2后面5个台阶有 8种跳) 21
     */
    public Long fibonacci(int n) {
        if (n <= 2) return Long.valueOf(n);
        Long first = 1L, second = 2L, result = 0L;

        for (int i = 2; i < n; i++) {
            result = first + second;
            first = second;
            second = result;
        }
        return result;
    }
    @Test
    public void test() {
        System.out.println(fibonacci(7));
    }
}
