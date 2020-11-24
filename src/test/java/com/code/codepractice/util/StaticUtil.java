package com.code.codepractice.util;

public  class StaticUtil<T> {
   public static <T> T test(Class<T> tClass) throws IllegalAccessException, InstantiationException {
     /*  T t=new T();//错误
       T[]arr=new T[3];//错误*/
       return tClass.newInstance();
   }
}
