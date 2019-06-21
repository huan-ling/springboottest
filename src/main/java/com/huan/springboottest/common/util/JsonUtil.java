package com.huan.springboottest.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * @Description: ${Description}
 * @Author: Huan
 * @CreateTime: 2019-02-15 15:50
 */
public class JsonUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtil.class);
    public static final ObjectMapper MAPPER = new ObjectMapper();

    public static String obj2Json(Object obj){
        try {
            return MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            LOGGER.error(e.getMessage());
        }
        return null;
    }

    /**
     *
     * @param json
     * @param clz
     * @param <T>
     * @return
     */
    public static <T> List<T> json2List(String json,Class<T> clz){
        try {
            JavaType javaType = MAPPER.getTypeFactory().constructParametricType(List.class, clz);
            return MAPPER.readValue(json, javaType);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
        return new ArrayList<>();
    }


    public static <T> T json2Obj(String json,Class<T> clz){
        try {
            return MAPPER.readValue(json, clz);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
        return null;
    }


    public static void main(String[] args) {
        String json = "[{\n" +
                "\t\"a\": \"a\",\n" +
                "\t\"b\": \"b\"\n" +
                "}, {\n" +
                "\t\"a\": \"a\",\n" +
                "\t\"b\": \"b\"\n" +
                "}]";
        List<Aoo> aoos = json2List(json, Aoo.class);
        System.err.println(aoos.get(0).getClass());
        Function<Integer,Integer> function = s->s+1;

        Integer apply = function.apply(1);
        System.err.println(apply);
    }

}

class Aoo{
    private String a;
    private String b;

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }
}
