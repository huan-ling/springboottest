package com.huan.springboottest.test;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.assertj.core.util.Lists;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: wb_xugz
 * @CreateTime: 2019-10-09 10:12
 */
public class MyTest2 {

    public static void main(String[] args) {
        List<String> list = Lists.newArrayList("1", "2", "3");
        List<String> collect = list.stream().map(t -> t.concat("1")).collect(Collectors.toList());
        System.out.println(collect);

        Arrays.sort(new Integer[]{1, 2}, (t1, t2) -> t2 - t1);

        Animal animal = Person::new;
        animal.set(1);
        System.out.println("animal = " + animal);
    }
}

@Getter
@Setter
@ToString
class Person {
    private int id;
    private String name;

    public Person(int id) {

    }
}

interface Animal {
    Object set(int id);
}
