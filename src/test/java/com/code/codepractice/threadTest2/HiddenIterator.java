package com.code.codepractice.threadTest2;

import javax.annotation.concurrent.GuardedBy;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * * <li>this : The string literal "this" means that this field is guarded by the class in which it is defined.
 *  * <li>class-name.this : For inner classes, it may be necessary to disambiguate 'this';
 *  * the class-name.this designation allows you to specify which 'this' reference is intended
 *  * <li>itself : For reference fields only; the object to which the field refers.
 *  * <li>field-name : The lock object is referenced by the (instance or static) field specified by field-name.
 *  * <li>class-name.field-name : The lock object is reference by the static field specified by class-name.field-name.
 *  * <li>method-name() : The lock object is returned by calling the named nil-ary method.
 *  * <li>class-name.class : The Class object for the specified class should be used as the lock object.
 * {@link  javax.annotation.concurrent.GuardedBy}
 * 1、@GuardedBy( "this" ) 受对象内部锁保护
 * 2、@GuardedBy( "fieldName" ) 受 与fieldName引用相关联的锁 保护。
 * 3、@GuardedBy( "ClassName.fieldName" ) 受 一个类的静态field的锁 保存。
 * 4、@GuardedBy( "methodName()" ) 锁对象是 methodName() 方法的返值，受这个锁保护。
 * 5、@GuardedBy( "ClassName.class" ) 受 ClassName类的直接锁对象保护。而不是这个类的某个实例的锁对象
 */
public class HiddenIterator {
    @GuardedBy("this")//加锁
    private final Set<Integer> set=new HashSet<>();
    public synchronized void add(Integer i){
        set.add(i);
    }
    public synchronized void remove(Integer i){
        set.remove(i);
    }
    public void addTenThings(){
        Random random=new Random();
        for (int i=0;i<10;i++){
            add(random.nextInt());
            System.out.println("added then elements to "+set);
            //方法可能抛出 ConcurrentModificationException 因为sout 输出信息时调用
            //toString 对容器进行迭代 ，迭代之前未获取HiddenIterator的锁
        }
    }
}
