package com.code.codepractice.algorithm;

import org.junit.Test;

/**
 * 有序数组 二分法查找
 */
public class BinarySearch {
    @Test
    public void test() {
        int[] sortArr = new int[]{45, 110, 9990, 11111, 888888};
        System.out.println(binary(sortArr, 1));
        System.out.println(binary(sortArr, 10000000));
        System.out.println(binary(sortArr, 112));
        System.out.println(binary(sortArr, 11111));
        System.out.println(binary(sortArr, 45));
        System.out.println(binary(sortArr, 888888));
        System.out.println(binary(sortArr, 110));
    }

    private int binary(int[] sortArr, int value) {
        if (sortArr.length == 0 || value < sortArr[0] || value > sortArr[sortArr.length - 1]) return -1;
        if (sortArr.length == 1 && sortArr[0] == value) return 0;
        int head = 0;
        int tail = sortArr.length - 1;
        while (head <= tail) {
            int middle = (head + tail) / 2;
            if (value == sortArr[middle]) {
                return middle;
            } else if (value < sortArr[middle]) {
                tail = middle - 1;//指针向小值区间 挪一个位置
            } else {
                head = middle + 1;//指针向大值区间 挪一个位置
            }
        }

        return -1;
    }
}
