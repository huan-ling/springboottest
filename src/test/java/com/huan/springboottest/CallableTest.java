package com.huan.springboottest;

import java.util.concurrent.FutureTask;

/**
 * @Author: wb_xugz
 * @CreateTime: 2020-07-22 15:31
 */
public class CallableTest {

    public static void main(String[] args) throws Exception {
//        String[] str = {
//                "1",
//                "2",
//                "1",
//                "3"
//        };
//        Arrays.stream(str).distinct().forEach(System.out::println);

        FutureTask<String> futureTask = new FutureTask<>(() -> {
            System.out.println("开始Callable");
            if (1 == 1) {
                throw new RuntimeException("test");
            }
            Thread.sleep(3000);
            System.out.println("结束Callable");
            return "1";
        });
        Thread thread = new Thread(futureTask);
        thread.start();
        Thread.sleep(1000);
        System.out.println("2");
        // 阻塞当前线程，等待子线程返回结果 类似于CountDownLatch await() countDown()
        try {
            // 如果此处代码注释掉，则控制台只输出 开始Callable 异常信息被吃了
            System.out.println(futureTask.get());
        } catch (Exception e) {
            throw e;
        }
        System.out.println("END");
    }
}
