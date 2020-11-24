package com.code.codepractice;

import javax.annotation.concurrent.GuardedBy;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

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
