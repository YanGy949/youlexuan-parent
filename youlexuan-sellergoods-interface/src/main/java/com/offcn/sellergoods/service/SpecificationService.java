package com.offcn.sellergoods.service;

import com.offcn.entity.PageResult;
import com.offcn.entity.SpecificationVO;
import com.offcn.pojo.TbSpecification;

import java.util.List;

public interface SpecificationService {
    /**
     * 查询所有的规格【不带条件不分页】
     * @return
     */
    public List<TbSpecification> findAll();

    /**
     * 查询所有的规格【带分页带条件】
     * @param pageNum
     * @param pageSize
     * @param tbSpecification
     * @return
     */
    public PageResult findPage(int pageNum,int pageSize,TbSpecification tbSpecification);
    /**
     * 添加规格
     * @param specificationVO
     */
    public void add(SpecificationVO specificationVO);

    /**
     * 根据id获取组合实体
     * @param id
     * @return
     */
    public SpecificationVO findOne(Long id);
    /**
     * 更新规格
     * @param specificationVO
     */
    public void update(SpecificationVO specificationVO);

    /**
     * 批量删除规格
     * @param ids
     */
    public void delete(Long[] ids);
}
