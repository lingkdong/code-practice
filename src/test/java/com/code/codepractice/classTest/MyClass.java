package com.code.codepractice.classTest;

import java.io.Serializable;

public class MyClass implements Cloneable, Serializable {
    public static final String MY_CLASS="MY_CLASS";
    private static final long serialVersionUID=1l;

    private String name;
    private String className;

    private MyClass(String name) {
        this.name = name;
    }

    public MyClass(String name, String className) {
        this.name = name;
        this.className = className;
    }

    public MyClass() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return "MyClass{" +
                "name='" + name + '\'' +
                ", className='" + className + '\'' +
                '}';
    }
    public void doMyClass(){
        System.out.println("do some work...");
    }
}
