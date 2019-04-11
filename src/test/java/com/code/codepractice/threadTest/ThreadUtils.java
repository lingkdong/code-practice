package com.code.codepractice.threadTest;

/**
 * @Author: dongxin
 * @Date: 2019/4/11 11:13
 **/
public  class ThreadUtils {

    public static void delayEnd(){
        // 延迟 test 结束
        delayEnd(2000);
    }
    public static void delayEnd(long millis){
        // 延迟 test 结束
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
