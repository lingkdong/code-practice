package com.code.codepractice.algorithm;

import org.junit.Test;

/**
 * 阶乘
 * 1*2*3*4*5...N
 * https://www.cs.usfca.edu/~galles/visualization/RecFact.html
 */
public class Factorail {
    @Test
    public void test() {
        System.out.println(factorial(4));
        System.out.println(factorial(10));
    }


    private Integer factorial(Integer n) {
        if (n <= 1) return 1;
        return n * factorial(n - 1);
    }
}
