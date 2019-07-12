package com.huan.springboottest.jdk8;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @Author: Huan
 * @CreateTime: 2019-06-24 17:23
 */
public class StreamUtil {

    public List<Person> getList() {
        List<Person> list = new ArrayList<>();
        list.add(new Person(1, "li", 18));
        list.add(new Person(2, "hua", 30));
        list.add(new Person(3, "li", 20));
        list.add(new Person(4, "wang", 24));
        list.add(new Person(5, "xu", 26));
        list.add(new Person(6, "jie", 27));
        return list;
    }

    @Test
    public void test1() {
        List<Person> list = getList();
        List<Person> collect = list.stream().filter(p -> p.age > 20).collect(Collectors.toList());
        System.out.println(collect);
    }

    @Test
    public void test2() {
        List<Person> list = getList();
        List<Integer> collect = list.stream().map(p -> p.getId()).collect(Collectors.toList());
        System.out.println(collect);

        // Map<id,person>
        Map<Integer, Person> map = list.stream().collect(Collectors.toMap(p -> p.getId(), p -> p));
        System.out.println(map);

        map = new HashMap<>();
        list.forEach(p -> System.out.println(p));
        list = new ArrayList<>();
    }

    class Person {
        private int id;
        private String name;
        private int age;

        public Person(int id, String name, int age) {
            this.id = id;
            this.name = name;
            this.age = age;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }

}
