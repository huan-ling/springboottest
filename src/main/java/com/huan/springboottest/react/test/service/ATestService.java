package com.huan.springboottest.react.test.service;

import com.huan.springboottest.react.test.pojo.StepField;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Huan
 * @CreateTime: 2019-06-21 10:46
 */
@Service
public class ATestService {

    public List<StepField> get(){
        List<StepField> list = new ArrayList<>();
        list.add(get(1,"填写基本信息"));
        list.add(get(2,"填写个人信息"));
        list.add(get(3,"填写工作信息"));
        list.add(get(4,"填写系统信息"));
        return list;
    }

    private StepField get(int id,String desc){
        StepField stepField = new StepField();
        stepField.setId(id);
        stepField.setDesc(desc);
        return stepField;
    }
}









