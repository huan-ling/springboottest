package com.huan.springboottest.test;

import java.util.concurrent.Semaphore;

/**
 * 信号量 相当于5个线程同时争抢三个通道
 * @Author: wb_xugz
 * @CreateTime: 2020-07-28 13:57
 */
public class SemaphoreTest {

    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(3);

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getId());
                    if (System.currentTimeMillis() % 2 == 0) {
                        Thread.sleep(3000);
                    } else {
                        Thread.sleep(1000);
                    }
                    semaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
        System.out.println("END");
    }
}
