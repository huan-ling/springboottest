package com.huan.springboottest.react.test.pojo;



import com.baomidou.mybatisplus.annotations.TableField;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;

/**
 * @Author: Huan
 * @CreateTime: 2019-07-15 10:03
 */
@Table(name = "user")
@Entity
public class User implements Serializable {

    private static final long serialVersionUID = 7464820959984426229L;
    @Id
    private int id;
    @Column(name = "u_name")
    private String uName;
    private int age;
    private String address;
    @Column(name = "dept_id")
    private int deptId;
    @Transient
    @TableField(exist = false)
    private String deptName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getDeptId() {
        return deptId;
    }

    public void setDeptId(int deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", uName='" + uName + '\'' +
                ", age=" + age +
                ", address='" + address + '\'' +
                ", deptId=" + deptId +
                ", deptName='" + deptName + '\'' +
                '}';
    }
}
