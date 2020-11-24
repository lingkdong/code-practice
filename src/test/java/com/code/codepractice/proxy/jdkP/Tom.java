package com.code.codepractice.proxy.jdkP;

/**
 * 目标类tom
 */
public class Tom implements HumanInterface {

    @Override
    public String born(String name) {
        return name+" born at summer";
    }

    @Override
    public void growUp(int step) {
        System.out.println("growUp "+step);
    }

    @Override
    public void visit(String... args) {

    }
}
