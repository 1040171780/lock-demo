package com.study.lock.reentrantLock;

import java.time.LocalDateTime;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 可响应中断
 */
public class ReentrantLockInterruptibly {
    private ReentrantLock lock = new ReentrantLock();
    public static void main(String[] args) throws InterruptedException {
        ReentrantLockInterruptibly demo = new ReentrantLockInterruptibly();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    demo.test();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread thread1 = new Thread(runnable);
        Thread thread2 = new Thread(runnable);
        thread1.setName("thread1");
        thread2.setName("thread2");
        thread1.start();
        Thread.sleep(500);
        thread2.start();
        Thread.sleep(2000);
        thread2.interrupt();
    }
    public void test() throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + "想获取锁" + getTime());
        lock.lockInterruptibly();
        try {
            System.out.println(Thread.currentThread().getName() + "获得到锁" + getTime());
            Thread.sleep(10000);
        } finally {
            lock.unlock();
            System.out.println(Thread.currentThread().getName() + "释放了锁" + getTime());
        }
    }

    private String getTime() {
        return LocalDateTime.now().toLocalTime().toString();
    }
}
