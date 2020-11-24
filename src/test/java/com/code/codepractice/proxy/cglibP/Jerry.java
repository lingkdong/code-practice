package com.code.codepractice.proxy.cglibP;

import java.util.Arrays;

/**
 * 目标类
 * cglib代理的 类 不能是final类型
 */
public class Jerry {
    public String born(String name) {
        return name+" born at summer";
    }

    public void growUp(int step) {
        System.out.println("growUp "+step);
    }

    public void visit(String... args) {
        System.out.println("visit citys:");
        Arrays.stream(args).forEach(item->{
            System.out.println(item);
        });
    }
}
