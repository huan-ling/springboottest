package com.huan.springboottest.react.test.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: Huan
 * @CreateTime: 2019-07-15 14:51
 */
public class DeptTreeDto implements Serializable {

    private static final long serialVersionUID = 4250443705445419686L;
    private int id;
    private String name;
    private String pId;
    private List<DeptTreeDto> deptList;

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
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

    public List<DeptTreeDto> getDeptList() {
        return deptList;
    }

    public void setDeptList(List<DeptTreeDto> deptList) {
        this.deptList = deptList;
    }

    @Override
    public String toString() {
        return "DeptTreeDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", pId='" + pId + '\'' +
                ", deptList=" + deptList +
                '}';
    }
}
