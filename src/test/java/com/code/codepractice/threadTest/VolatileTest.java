package com.code.codepractice.threadTest;

/**
 * volatile 可见性
 * volatile修饰的数据 cpu不可乱序执行 屏障来实现 hotspot 底层是是通过cpu 的 lock 指令 锁总线实现
 * volatile修饰的数据 缓存失效策略  工作内存 会从主存重新读取数据 实现可见性
 * 可以查看jdk 源码
 * https://github.com/openjdk/jdk
 * bytecodeInterpreter.cpp->is_volatile OrderAccess::storeload();//storeload屏障
 *orderAccess_linux_x86.hpp->
 * inline void OrderAccess::storeload()  { fence();            }
 *inline void OrderAccess::fence() {
 *    // always use locked addl since mfence is sometimes expensive
 *    //lock;指令锁总线
 * #ifdef AMD64
 *   __asm__ volatile ("lock; addl $0,0(%%rsp)" : : : "cc", "memory");
 * #else
 *   __asm__ volatile ("lock; addl $0,0(%%esp)" : : : "cc", "memory");
 * #endif
 *   compiler_barrier();
 * }
 *
 * // A compiler barrier, forcing the C++ compiler to invalidate all memory assumptions
 * //设置 memory 无效 缓存无效 需要重新同步 工作内存和主内存
 * static inline void compiler_barrier() {
 *   __asm__ volatile ("" : : : "memory");
 * }
 */
public class VolatileTest {
    //private static  boolean running = true;
    private static volatile boolean running = true;

    public static void main(String[] args) {
        try {
            new Thread(() -> {
                System.out.println("start...running="+running);
                while (running) {
                    //如果 running中是输出语句 程序会关闭
                    //输出语句会不定时同步主存和工作内存数据
                    //所以不用volatile 程序不确定关闭
                }
                //thread1程序确一直没end 所以要加上volatile 主内存和工作内存数据同步
                System.out.println("end...running="+running);//加上volatile后此句会被执行
            }).start();

            Thread thread2 = new Thread(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                running = false;
                System.out.println("running =" +running);
            });

            thread2.start();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
