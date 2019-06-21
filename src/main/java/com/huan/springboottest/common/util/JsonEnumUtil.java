package com.huan.springboottest.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 枚举转换成json,包含枚举中的详细信息
 * @Author: Huan
 * @CreateTime: 2019-02-15 18:20
 */
public class JsonEnumUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonEnumUtil.class);

    public static String enum2Json(Class<? extends Enum> clz){
        Field[] declaredFields = clz.getDeclaredFields();
        List<String> filedNameList = new ArrayList<>();
        for (Field filed: declaredFields) {
            if(!filed.isEnumConstant() && !filed.isSynthetic()){
                filedNameList.add(filed.getName());
            }
        }

        List<Map> list = new ArrayList<>();
        for (Object enumObj : clz.getEnumConstants()) {
            Map<String,String> map = new HashMap<>();
            map.put("name", ((Enum)enumObj).name());
            for (String filedName : filedNameList) {
                String methodName = "get" + filedName.substring(0,1).toUpperCase()+filedName.substring(1);
                try {
                    Method declaredMethod = clz.getDeclaredMethod(methodName, null);
                    Object invoke = declaredMethod.invoke(enumObj, null);
                    map.put(filedName,invoke.toString());
                } catch (Exception e) {
                    LOGGER.error("反射调用方法异常",e);
                }
            }
            list.add(map);
        }
        return JsonUtil.obj2Json(list);
    }

}
