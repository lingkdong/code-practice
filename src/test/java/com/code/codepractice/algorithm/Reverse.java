package com.code.codepractice.algorithm;

import org.junit.Test;

/**
 * 字符串翻转
 * how to use recursive
 * 和阶乘类似
 * n*factorial(n-1)
 */
public class Reverse {
    @Test
    public void test(){
        String data="123456789";
        System.out.println(reverse(data));
        char []chars=data.toCharArray();
        System.out.println(reverse(chars,chars.length-1));
    }

    private String reverse(String str){
        if(str==null||str.length()<=1)return str;
        char[] chars=str.toCharArray();
        String newData="";
        for(int i=str.length()-1;i>=0;i--){
            newData=newData+chars[i];
        }
        return newData;
    }

    private String reverse(char[] chars,int n){
        if(n<1)return String.valueOf(chars[0]);
        return chars[n]+reverse(chars,n-1);
    }

}
