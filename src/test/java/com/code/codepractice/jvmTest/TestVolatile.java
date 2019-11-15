package com.code.codepractice.jvmTest;

/**
 * @Author: dongxin
 * @Date: 2019/9/30 13:56
 **/
public class TestVolatile {
    private volatile boolean shownDown;

    public TestVolatile() {
        this.shownDown = true;
    }

    public void doWork() {
        while (!shownDown) {
            //do
        }
    }
}
