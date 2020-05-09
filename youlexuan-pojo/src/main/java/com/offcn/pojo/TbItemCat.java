package com.offcn.pojo;

import java.io.Serializable;
import java.util.List;

public class TbItemCat implements Serializable {

    private List<TbItemCat> list;//当前分类的下一级分类

    public List<TbItemCat> getList() {
        return list;
    }

    public void setList(List<TbItemCat> list) {
        this.list = list;
    }

    private Long id;

    private Long parentId;

    private String name;

    private Long typeId;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }
}