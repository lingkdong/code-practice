package com.code.codepractice.proxy.cglibP;

import net.sf.cglib.proxy.*;
import org.junit.Test;

import java.lang.reflect.Method;

public class JerryProxyTest {
    /**
     * 使用 Callback和CallbackFilter 设计灵活
     *   Callback 四种实现类型 一般用MethodIntercepter
     * {@link net.sf.cglib.proxy.Callback}
     * MethodInterceptor
     * NoOp
     * LazyLoader
     * Dispatcher
     * InvocationHandler
     * FixedValue 替换方法的返回值为回调方法的返回值，但必须保证返回类型是兼容的，否则会出转换异常。
     *
     * CallbackFilter 作用 请看
     * CallBackFilterTest
     */
    public static void main(String[] args) {
        Jerry jerryProxy = (Jerry) Enhancer.create(Jerry.class, (MethodInterceptor) (o, method, args1, methodProxy) -> {
            //增强born 方法
            if ("born".equals(method.getName())) {
                System.out.println("hello,everyone,this is " + args1[0]);
//                Object result = method.invoke(jerry, args1);
                Object result = methodProxy.invokeSuper(o, args1);//访问更快 为方法建立的了索引
                System.out.println(result);
                ;//执行原 born.
                System.out.println("thank u");
                return result;
            } else {
                return methodProxy.invokeSuper(o, args1);//访问更快 为方法建立的了索引
            }

        });
        Jerry jerry = new Jerry();
        System.out.println(jerry.born("little jerry"));
        System.out.println(jerryProxy.born("little jerry"));


    }
    @Test
    public void CallBackFilterTest(){
        Enhancer enhancer=new Enhancer();
        enhancer.setSuperclass(Jerry.class);
        CallbackHelper callbackHelper=new CallbackHelper(Jerry.class,new Class[0]) {
            @Override
            protected Object getCallback(Method method) {
               if(void.class==method.getReturnType()){
                   return (MethodInterceptor) (obj, method1, args, proxy) -> {
                       System.out.println(method1.getName()+" start, return type is void");
                       return proxy.invokeSuper(obj,args);
                   };
               }else if(String.class==method.getReturnType()){
                   return (FixedValue) () -> {
                       System.out.println(method.getName()+" start, return type is String");
                       return "I am new return value of proxy";//取代原来返回值
                   };
                }else return NoOp.INSTANCE;//回调方法直接委托给基类，调用原方法
            }
        };
        enhancer.setCallbacks(callbackHelper.getCallbacks());
        enhancer.setCallbackFilter(callbackHelper);
        Jerry jerryProxy= (Jerry) enhancer.create();
        System.out.println(jerryProxy.born("little jerry"));
       jerryProxy.growUp(3);
       jerryProxy.visit("beijing","shanghai","newYork");
    }
}
