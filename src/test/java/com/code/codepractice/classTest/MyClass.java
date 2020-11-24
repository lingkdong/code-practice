package com.code.codepractice.classTest;

public class MyClass {
    private String name;
    private String className;

    public MyClass(String name) {
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
    public String toString() {
        return "MyClass{" +
                "name='" + name + '\'' +
                ", className='" + className + '\'' +
                '}';
    }
}
