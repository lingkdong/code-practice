package com.code.codepractice.proxy.jdkP;

import java.lang.reflect.Proxy;

public class TomProxyTest {
    public static void main(String[] args) {
        //目标类 tom
        Tom tom=new Tom();
        //代理类
       HumanInterface humanInterface= (HumanInterface) Proxy.newProxyInstance(tom.getClass().getClassLoader(),
                tom.getClass().getInterfaces(),
                (proxy, method, args1) -> {
                   //增强born 方法
                    if("born".equals(method.getName())){
                        System.out.println("hello,everyone,this is "+ args1[0]);
                        Object result = method.invoke(tom, args1);
                        System.out.println(result);;//执行原 born.
                        System.out.println("thank u");
                        return result;
                    }else {
                       return method.invoke(tom, args1);//利用反射执行tom中的原方法
                    }
                }
        );
        System.out.println(tom.born("tom"));
        tom.growUp(1);
        System.out.println("proxy....");
       humanInterface.born("tom");
       humanInterface.growUp(1);
    }
}
