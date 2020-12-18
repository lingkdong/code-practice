package com.code.codepractice.classTest;

import org.junit.Test;

import java.io.*;
import java.lang.reflect.*;
import java.util.Arrays;
import java.util.Properties;

/**
 * 创建对象方式
 * 1.通过new 创建
 * 2.通过反射创建
 *     2.1 forName
 *        newInstance() 需要无参构造函数
 *        Construct     有参数通过构造函数
 *     2.2 ClassLoader
 *        newInstance() 需要无参构造函数
 *        Construct     有参数通过构造函数
 * 3. 通过浅Clone创建
 *    对象要实现 Cloneable 接口 Object 类中的 clone是 protected的
 *    对象要 override clone
 *     public Object clone() throws CloneNotSupportedException {
 *         return super.clone();
 *     }
 * 4.反序列化对象 对象要实现 Serializable接口
 *   通过ObjectInputStream 读取对象后反序列化
 *   inputStream.readObject();
 *
 * java 反射特别重要
 * 反射可以节约编码 解耦， 程序配置性提高,也可以在程序运行中操作对象
 * {@link java.lang.reflect}
 */
public class ClassTestDemo {
    /**
     * new 创建类
     */
    @Test
    public void byNew() {
        MyClass myClass = new MyClass("test", "new");
        System.out.println(myClass);
    }

    /**
     * 利用反射创建
     * 1.使用Class.forName
     * 2.使用ClassLoader
     */
    @Test
    public void byforName() {
        try {
            //1.用 forName创建
            MyClass myClass = (MyClass) Class.forName("com.code.codepractice.classTest.MyClass")
                    .newInstance();//必须要有无参构造函数
            System.out.println(myClass.toString());

            //2.如果没有无参构造函数 使用construct创建类对象
            Class<?> obj = Class.forName("com.code.codepractice.classTest.MyClass");
            Constructor<?>[] constructors = obj.getConstructors();//获取所有构造函数
            Constructor<?> constructor = obj.getConstructor(String.class, String.class);//获取指定构造函数
            MyClass myClass2 = (MyClass) constructor.newInstance("test", "constructor");//有参
            System.out.println(myClass2);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * 利用反射创建
     * 1.使用Class.forName
     * 2.使用ClassLoader
     */
    @Test
    public void byClassLoader() {
        try {
            //1.无参构造函数
            MyClass myClass = (MyClass) ClassLoader.getSystemClassLoader().loadClass(MyClass.class.getName()).newInstance();//无参构造函数
            System.out.println(myClass);
            //2.有参构造函数
            Class<?> obj = ClassLoader.getSystemClassLoader().loadClass(MyClass.class.getName());
            Constructor<?> constructor = obj.getConstructor(String.class);
            MyClass myClass2= (MyClass) constructor.newInstance("by class loader");
            System.out.println(myClass2);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * 浅Clone创建
     * 对象要实现Cloneable接口
     */
    @Test
    public void byClone(){
        try {
            MyClass myClass=new MyClass("by clone","by clone");
            MyClass myClass2= (MyClass) myClass.clone();
            System.out.println(myClass2);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 反序列化对象
     * 根据流创建对象
     */
    @Test
    public void byStream(){
        serializeObj();
        //反序列化对象
        try ( ObjectInputStream inputStream=new ObjectInputStream(new FileInputStream("D://data//ObjSerialize"))){
            MyClass myClass= (MyClass) inputStream.readObject();
            System.out.println(myClass);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void serializeObj(){
        //1.序列化对象 将对象写入文件
        try(ObjectOutputStream outputStream=new ObjectOutputStream(new FileOutputStream("D://data//ObjSerialize"));) {
            MyClass myClass=new MyClass("by Serializable","by Serializable");
            outputStream.writeObject(myClass);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Class 类
     * {@link java.lang.Class}
     * 类里面主要的对象
     * Field:属性 {@link java.lang.reflect.Field}
     * Construct:构造函数 {@link java.lang.reflect.Constructor}
     * Method:方法 {@link java.lang.reflect.Method}
     * Parameter:参数{@link java.lang.reflect.Parameter}
     */
    @Test
    public void viewClass(){
        try {
            //查看输出下类中这些对象
            Class<?> aClass = ClassLoader.getSystemClassLoader().loadClass(MyClass.class.getName());
            Class<?> aClass2 = ClassLoader.getSystemClassLoader().loadClass(MyClass.class.getName());
            Class<?> aClass3 = Class.forName(MyClass.class.getName());
            System.out.println(aClass==aClass2);//一次运行中同一个类加载器 加载的类 只加载一次
            System.out.println(aClass==aClass3);//一次运行中同一个类加载器 加载的类 只加载一次
            //获取公共属性 public fields 并打印
            Field[] fields = aClass.getFields();
            /**
             *  Field.toString()
             *    public String toString() {
             *         int mod = getModifiers();
             *         return (((mod == 0) ? "" : (Modifier.toString(mod) + " "))
             *             + getType().getTypeName() + " "
             *             + getDeclaringClass().getTypeName() + "."
             *             + getName());
             *     }
             * Modifiers:修饰符 {@link java.lang.reflect.Modifier }
             * Modifiers.toString(mod)将修饰符对应的值 转换成String 如 public 修饰符的值是 0x00000001（16进制）标识1
             * 16 进制 1,2,3,4,5,6,7,8,9,A,B,C,D,E,F
             * F标识15
             */
            Arrays.stream(fields).forEach(item->{
                try {
                    System.out.println((((item.getModifiers() == 0) ? ""
                            : (Modifier.toString(item.getModifiers()) + " "))//Modifiers.toString(mod)将修饰符对应的值 转换成String 如 public,private
                            + item.getType().getSimpleName() + " "//这里我们用SimpleName 这样就没有 前面的报名
                         /*   + item.getDeclaringClass().getTypeName() + "."*/ //类名我们也不要了
                            + item.getName())+" = "//属性名称
                            +item.get(aClass)//对象作为参数获取其对应Field值
                            +" ;"
                    );
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            });
            //获取全部属性 all fields 并打印
            Field[] declaredFields = aClass.getDeclaredFields();
            Arrays.stream(declaredFields).forEach(item->{
                try {
                    System.out.println(item.toGenericString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            //获取全部属性 all fields，修该属性并打印
            Field[] declaredFields2 = aClass.getDeclaredFields();
            Object object=aClass.newInstance();//实例化对象
            Arrays.stream(declaredFields2).forEach(item->{
                try {
                    //String 类型 且不是final的 我们设置新值
                    if(item.getType()==String.class && !Modifier.isFinal(item.getModifiers())){
                        item.setAccessible(true);//暴力反射 设置无障碍修改属性
                        //修改对象属性
                        item.set(object,"为"+item.getName()+"设置新的属性值");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            System.out.println(object);
            //get public constructs and print
            Constructor<?>[] constructors = aClass.getConstructors();
            Arrays.stream(constructors).forEach(item->{
                System.out.println(item.toGenericString());
            });
            //get all constructs and print
            Constructor<?>[] declaredConstructors = aClass.getDeclaredConstructors();
            Arrays.stream(declaredConstructors).forEach(item->{
                System.out.println(item.toGenericString());
            });
            //get public methods and print
            Method[] methods = aClass.getMethods();
            Arrays.stream(methods).forEach(item->{
                System.out.println(item.toGenericString());
            });
            //get all methods and print
            Method[] declaredMethods = aClass.getDeclaredMethods();
            Arrays.stream(declaredMethods).forEach(item->{
                System.out.println(item.toGenericString());
            });
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    /**
     * 16进制测试
     */
    @Test
    public void hexTest(){
        System.out.println(0x00000001);//1
        System.out.println(0x00000009);//9
        System.out.println(0x0000000A);//10
        System.out.println(0x0000000F);//15
        System.out.println(0x000000010);//16
        System.out.println(0x000000011);//17=16*1+1
        System.out.println(0x000000020);//32=16*2
        System.out.println(0x000000040);//64=16*4
        System.out.println(0x0000000F0);//240=16*15
        System.out.println(0x000000100);//256=(16*16)*1
        System.out.println(0x000000790);//1936=(16*16)*7+16*9
        System.out.println(0x000001310);//(16*16*16)*1+(16*16)*3+16*1=4880


    }

    /**
     * 根据配置文件执行类，这样我们只要修改配置文件不改代码就可以执行不同的类方法
     * 在配置文件中配置相关属性
     * 通过反射加载运行代码
     * 解耦代码，提高代码的可配置性
     */
    @Test
    public void executeMethodByConfig(){
        try(InputStream inputStream=ClassTestDemo.class.getClassLoader().getResourceAsStream("application.properties")) {
            Properties properties=new Properties();
            properties.load(inputStream);
           String className= (String) properties.get("className");
           String methodName= (String) properties.get("methodName");
            Class<?> aClass = Class.forName(className);//加载类
            Method method = aClass.getMethod(methodName);
            Object obj=aClass.newInstance();//实例化对象
            method.invoke(obj);//执行对象方法
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }
}
