package com.huan.springboottest.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.cglib.beans.BeanCopier;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: ${Description}
 * @Author: Huan
 * @CreateTime: 2019-02-24 17:33
 */
public class BeanUtis {
    /**
     * 存储BeanCoper对象
     */
    public static Map<String, BeanCopier> beanCopierMap = new HashMap<String, BeanCopier>();

    private static Logger logger = LoggerFactory.getLogger(BeanUtils.class);
    /**
     * 转换时对map中的key里的字符串会做忽略处理的正则串
     */
    private static final String OMIT_REG = "_";

    /**
     * 将source的所有对象copy到desc中
     *
     * @param source
     *            原对象列表
     * @param desc
     *            目标对象列表
     * @param descClazz
     *            目标对象类型
     */
    public static void copyListProperties(List source, List desc, Class descClazz) {
        for (Object o : source) {
            try {
                Object d = descClazz.newInstance();
                copyProperties(o, d);
                desc.add(d);
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

    }

    /**
     * 对象赋值
     *
     * @param source
     * @param target
     */
    public static void copyProperties(Object source, Object target) {
        if (source != null) {
            String beanKey = generateKey(source.getClass(), target.getClass());
            if (!beanCopierMap.containsKey(beanKey)) {
                BeanCopier copier = BeanCopier.create(source.getClass(), target.getClass(), false);
                beanCopierMap.put(beanKey, copier);
            }
            beanCopierMap.get(beanKey).copy(source, target, null);
        }
    }

    private static String generateKey(Class<?> cls1, Class<?> cls2) {
        return cls1.toString() + cls2.toString();
    }

}
