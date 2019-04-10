package com.code.codepractice;

import org.junit.Test;

/**
 * @Author: dongxin
 * @Date: 2019/2/25 10:41
 **/
public class ProgramTest {
    @Test
    public void test(){
        System.out.println(fibonacci(7));
        System.out.println(ratio(7));
    }
/***************************fibonacicc number***************************/
    /**
     * fibonacci number
     * 斐波那切数列
     * 1,2,3,5,8,13,21
     */
    public Long fibonacci(int n){
        if(n<=2)return Long.valueOf(n);
        Long first=1L,second=2L,result=0L;

        for(int i=2;i<n;i++){
         result=first+second;
         first=second;
         second=result;
        }
        return result;
    }
    /**
     * 跳阶(1,2) 也是fibonacii number
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

    /***************************fibonacicc number***************************/


    /***************************ratio number***************************/

    /**
     * 跳阶(1,2,3,4 ...n)
     * step 1 or 2 or 3 or 4 ...or n;
     * n个台阶有几种跳法
     * 递推
     * 台阶数                                                                               跳法
     * 1                                                                                           1
     * 2                                                                                           2
     * 3 (先1后2，2种；先2后1,1种；先3，1种) 2+1+1                                                 4
     * 4 (先1后3，4种；先2后2,2种；先3后1，1种，先4 1种) 4+2+1+1                                   8
     * 5 (先1后4，8种；先2后3,4种；先2后2，2种，先4后1，1种；先5 1种) 8+4+2+1+1                    16
     * 6 (先1后5，16种；先2后4,8种；先3后3，4种，先4后2，2种；先5后1 1种 先6 1种) 16+8+4+2+1+1     32
     * 2的(n-1)次方
     * 等比例数列
     */
     public Double ratio(int n){
         return Math.pow(2,n-1);
     }

    /***************************ratio number***************************/


    @Test
    public void testReplace(){
        System.out.println(Math.pow(2,8));
        System.out.println(Math.log(65536)/Math.log(2));
        System.out.println(Math.pow(2,16));
    }


    @Test
    public void testSwitch(){
       sw(1);
       sw(2);
       sw(5);
    }

    private void sw(int value){
        switch (value){
            case 1:
                System.out.println(value);
                break;
            case 2:
            case 3:
            case 4:
                System.out.println("not 1 value="+value);
                break;
            case 5:
                System.out.println(value);
                break;
        }
    }


    @Test
    public void testStringFormat(){
        System.out.println(String.format("this is test %s,%s","format",1));
    }
}
