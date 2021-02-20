package com.code.codepractice.jvmTest.jol;

import com.code.codepractice.classTest.MyClass;
import com.code.codepractice.jvmTest.TestClass;
import org.junit.Test;
import org.openjdk.jol.info.ClassLayout;

/**
 * java.lang.Object object internals:
 *  OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
 *       0     4        (object header)                           01 00 00 00 (00000001 00000000 00000000 00000000) (1)
 *       4     4        (object header)                           00 00 00 00 (00000000 00000000 00000000 00000000) (0)
 *       8     4        (object header)                           28 0f b3 16 (00101000 00001111 10110011 00010110) (380833576)
 *      12     4        (loss due to the next object alignment)
 * Instance size: 16 bytes
 * Space losses: 0 bytes internal + 4 bytes external = 4 bytes total
 * mark word 8个字节
 * 一个对象 初始化是 16个字节
 * jvm 第二版中
 * 存储内容                        标志位               状态
 * 对象hash码，分代年龄              01                  未锁定
 * 指向锁记录的指针                  00                  轻量级锁定
 * 重量级锁定指针                    10                  膨胀锁 重量级锁
 * 空，不需要记录信息                11                  GC垃圾回收标记
 * 偏向线程ID,偏向时间戳，           01                  可偏向
 *   对象分代年龄
 *
 * https://www.jianshu.com/p/993628f0f4bd
 *
 * 源码 markWord.hpp
 *
 *  //    [JavaThread* | epoch | age | 1 | 01]       lock is biased toward given thread
 * //    [0           | epoch | age | 1 | 01]       lock is anonymously biased
 * //
 * //  - the two lock bits are used to describe three states: locked/unlocked and monitor.
 * //
 * //    [ptr             | 00]  locked             ptr points to real header on stack
 * //    [header      | 0 | 01]  unlocked           regular object header
 * //    [ptr             | 10]  monitor            inflated lock (header is wapped out) 膨胀锁
 * //    [ptr             | 11]  marked             used to mark an object
 * //    [0 ............ 0| 00]  inflating          inflation in progress
 */
public class JolTest {

    @Test
    public void testPrint(){
        Object object=new Object();
        System.out.println(ClassLayout.parseInstance(object).toPrintable());
        //加锁 mark word有变化
        //mark word 有三个信息 1.锁信息 2.GC 3.identity hashcode
        synchronized (object){
            System.out.println("....................................");
            System.out.println(ClassLayout.parseInstance(object).toPrintable());

        }

        object.hashCode();
        System.out.println("....................................");
        System.out.println(ClassLayout.parseInstance(object).toPrintable());


//        System.out.println("....................................");
//        TestClass object2=new TestClass();
//        System.out.println(ClassLayout.parseInstance(object2).toPrintable());
//
//        System.out.println("....................................");
//        TestClass object3=new TestClass();
//        object3.add(11);
//        System.out.println(ClassLayout.parseInstance(object3).toPrintable());



    }
}
