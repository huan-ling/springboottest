package com.huan.springboottest.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author: wb_xugz
 * @CreateTime: 2019-11-13 13:50
 */
@Getter
@Setter
@ToString
public class TestDto {

    private int id;
    private String name;

    public static void main(String[] args) {
        TestDto testDto = new TestDto();
        testDto.setId(12);
        System.out.println("testDto = " + testDto);
    }

}
