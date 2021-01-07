package com.code.codepractice.threadTest;

public class VolatileTest {
//    private static  boolean flag = true;
    private static volatile boolean flag = true;

    public static void main(String[] args) {
        try {
           /* for(int i=0;i<10;i++){*/
                new Thread(() -> {
                    while (flag) {
                        System.out.println("still running...");
                    }
                }).start();
          /*  }*/

            Thread thread2 = new Thread(() -> {
                flag = false;
                System.out.println("change flag to false");
            });

            thread2.start();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
