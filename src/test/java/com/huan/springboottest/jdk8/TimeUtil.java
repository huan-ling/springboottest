package com.huan.springboottest.jdk8;

import org.junit.Test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

/**
 * @Author: Huan
 * @CreateTime: 2019-06-24 16:09
 */
public class TimeUtil {

    @Test
    public void test1() {
        /**
         *
         */
        LocalDateTime dateTime = LocalDateTime.now();
        System.out.println(dateTime);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        dtf.format(dateTime);
        System.out.println(dtf.format(dateTime));

        String str = "2008-08-08 01:00:00";
        LocalDateTime parse = LocalDateTime.parse(str, dtf);
        System.out.println(parse);
        LocalTime time = LocalTime.parse(str,dtf);
        System.out.println(time);

        Instant instant = new Date().toInstant();
        instant.atOffset(ZoneOffset.UTC).toLocalDateTime();
        System.out.println(instant);
    }

}
