package com.offcn.sellergoods.service;

import com.offcn.entity.PageResult;
import com.offcn.pojo.TbBrand;

import java.util.List;

public interface BrandService {
    /**
     * 返回所有列表
     * @return
     */
    public List<TbBrand> findAll();

    /**
     * 返回分页列表
     * pageNum：第几页
     * pageSize：每页显示多少条数据
     * @param pageNum
     * @param pageSize
     * @return
     */
    public PageResult findPage(int pageNum, int pageSize);

    /**
     * 带条件的分页
     * @param tbBrand 实体【条件】
     * @param pageNum 页码
     * @param pageSize 每页的数据数
     * @return
     */
    public PageResult findPage(TbBrand tbBrand, int pageNum,int pageSize);
    /**
     * 增加品牌
     * @param tbBrand
     */
    public void add(TbBrand tbBrand);

    /**
     * 根据id获取实体
     * @param id
     * @return
     */
    public TbBrand findOne(Long id);

    /**
     * 修改品牌
     * @param tbBrand
     */
    public void update(TbBrand tbBrand);

    /**
     * 删除品牌
     * @param ids
     */
    public void delete(Long [] ids);



}
