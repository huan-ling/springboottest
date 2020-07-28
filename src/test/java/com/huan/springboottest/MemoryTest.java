package com.huan.springboottest;

import org.apache.commons.lang3.time.StopWatch;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;

/**
 * @Author: wb_xugz
 * @CreateTime: 2020-06-23 10:52
 */
public class MemoryTest {

    public static void main(String[] args) throws Exception{
        StopWatch stopWatch = StopWatch.createStarted();
        Thread.sleep(1000);
        stopWatch.stop();
        System.out.println(stopWatch.getTime());

        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        // 椎内存使用情况
        MemoryUsage memoryUsage = memoryMXBean.getHeapMemoryUsage();
        // 初始的总内存
        long totalMemorySize = memoryUsage.getInit();
        // 已使用的内存
        long usedMemorySize = memoryUsage.getUsed();

        System.out.println("Total Memory: " + totalMemorySize / (1024 * 1024) + " Mb");
        System.out.println("Free Memory: " + usedMemorySize / (1024 * 1024) + " Mb");
    }
}
