package com.huan.springboottest.react.test.pojo;

import java.io.Serializable;

/**
 * @Author: Huan
 * @CreateTime: 2019-07-15 14:15
 */
public class Dept implements Serializable {
    private static final long serialVersionUID = -3205292389231941532L;
    private int id;
    private String name;
    private int pId;

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

    public int getPId() {
        return pId;
    }

    public void setPId(int PId) {
        this.pId = PId;
    }

    @Override
    public String toString() {
        return "Dept{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", pId=" + pId +
                '}';
    }
}
