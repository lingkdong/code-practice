package com.code.codepractice.classTest;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * 创建对象方式
 */
public class ClassTestDemo {
    @Test
    public void byNew(){
        MyClass myClass=new MyClass("test");
        System.out.println(myClass);
    }

    /**
     *
     * @throws ClassNotFoundException
     */
    @Test
    public void byForName() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
       MyClass myClass= (MyClass) Class.forName("com.code.codepractice.classTest.MyClass")
               .newInstance();//必须要有无参构造函数
        System.out.println(myClass.toString());
        //2.如果没有无参构造函数 使用construct创建类对象
    }

    @Test
    public void byConstruct() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<?>obj=Class.forName("com.code.codepractice.classTest.MyClass");
        Constructor<?>[] constructors = obj.getConstructors();//获取所有构造函数
        Constructor<?> constructor = obj.getConstructor(String.class, String.class);//获取指定构造函数
        MyClass myClass= (MyClass) constructor.newInstance("test","constructor");//有参

        System.out.println(myClass);
    }

}
