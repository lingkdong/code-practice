package com.code.codepractice.proxy.jdkP;

/**
 * jdk代理 必须要有父类实现接口
 */
public interface HumanInterface {
    String born(String name);
    void growUp(int step);
    void visit(String ... args);
}
