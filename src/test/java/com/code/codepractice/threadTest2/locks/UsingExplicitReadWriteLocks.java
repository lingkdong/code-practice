package com.code.codepractice.threadTest2.locks;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Author: dongxin
 * @Date: 2019/5/13 10:12
 * 显示读写锁
 * special lock with a different strategy: allow multiple readers simultaneously
 * and only one signle writer
 * 允许多个读操作，允许一个写操作
 * 写操作是排他锁：读写不会同时进行
 **/
public class UsingExplicitReadWriteLocks {
    private ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private List<String> list = new ArrayList<>();
    private String content = "a long default content...";

    public String readContent() throws InterruptedException {
        ReentrantReadWriteLock.ReadLock readLock = readWriteLock.readLock();
        readLock.lock();
        try {
            Thread.sleep(1);//读一次至少消耗 1 millis
            list.add(content);
            return content;
        } finally {
            readLock.unlock();
        }

    }

    public void writeContent(String newAppend) throws InterruptedException {
        ReentrantReadWriteLock.WriteLock writeLock = readWriteLock.writeLock();
        writeLock.lock();
        try {
            content = new StringBuilder(content).append(newAppend).toString();
            Thread.sleep(1);//写一次至少消耗 1 millis
            list.add(content);
        } finally {
            writeLock.unlock();
        }

    }

    @Test
    public void testRead() throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        UsingExplicitReadWriteLocks self = new UsingExplicitReadWriteLocks();
        //read 100次 查看消耗时间
        long readStart = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            executorService.submit(() -> {
                try {
                    self.readContent();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.DAYS);//等待完成
        System.out.println("read cost:" + (System.currentTimeMillis() - readStart));
        System.out.println("list size:" + self.list.size());
      /*  结果:
        read cost:60
        list size:100
        size>cost(说明并发执行，因为list add一次至少 1mills)
        */
    }

    @Test
    public void testWrite() throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        UsingExplicitReadWriteLocks self = new UsingExplicitReadWriteLocks();
        //read 100次 查看消耗时间
        long readStart = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            executorService.submit(() -> {
                try {
                    self.writeContent(UUID.randomUUID().toString());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            });
        }
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.DAYS);//等待完成
        System.out.println("write cost:" + (System.currentTimeMillis() - readStart));
        System.out.println("list size:" + self.list.size());
      /*  结果:
        write cost:331
        list size:100
        */
    }

    @Test
    public void testReadAndWrite() throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        UsingExplicitReadWriteLocks self = new UsingExplicitReadWriteLocks();
        //read 100次 查看消耗时间
        long readStart = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            executorService.submit(() -> {
                try {
                    self.readContent();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            });
        }
        executorService.execute(() -> {
            try {
                self.writeContent(UUID.randomUUID().toString());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.DAYS);//等待完成
        System.out.println("read and write cost:" + (System.currentTimeMillis() - readStart));
        System.out.println("list size:" + self.list.size());

      /*  结果：
        read and write cost:138
        list size:101
        */
    }

}
