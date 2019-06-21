package com.huan.springboottest.common.exception;

/**
 * @Description: ${Description}
 * @Author: Huan
 * @CreateTime: 2019-02-15 18:33
 */
public class BaseException extends RuntimeException{

    private int code;
    private String desc;

    public BaseException(){

    }

    public BaseException(String desc){
        super(desc);
        this.desc = desc;
    }

    public BaseException(int code,String desc){
        super(desc);
        this.code = code;
        this.desc = desc;
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

    @Override
    public String toString() {
        return "BaseException{" +
                "code=" + code +
                ", desc='" + desc + '\'' +
                '}';
    }
}
