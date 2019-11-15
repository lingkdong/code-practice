package com.code.codepractice.jvmTest;

/**
 * @Author: dongxin
 * @Date: 2019/9/29 13:27
 **/
public class TestClass {
    private String name;

    public TestClass(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassDetail() {
        //逃逸分析 其他优化的依据
        String detail= TestClass.class + " name=" + getName();//编译时方法内联优化
        return detail;//冗余访问消除
    }

    public String getClassDetail2() {
        return TestClass.class + " name=" + this.name;
    }
}
