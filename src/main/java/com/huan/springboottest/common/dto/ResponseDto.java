package com.huan.springboottest.common.dto;

import java.io.Serializable;

/**
 * @Description: ${Description}
 * @Author: Huan
 * @CreateTime: 2019-02-15 18:21
 */
public class ResponseDto<T> implements Serializable {

    private static final long serialVersionUID = -3656063683387614529L;
    private int code;
    private String desc;
    private T data;

    public ResponseDto(){
        this(200,"success",null);
    }

    public ResponseDto(T data){
        this(200,"success", data);
    }

    public ResponseDto(int code, String desc, T data){
        this.code = code;
        this.desc = desc;
        this.data = data;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResponseDto{" +
                "code=" + code +
                ", desc='" + desc + '\'' +
                ", data=" + data +
                '}';
    }
}
