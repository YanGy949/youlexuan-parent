package com.offcn.sellergoods.service;

import com.offcn.entity.PageResult;
import com.offcn.pojo.TbTypeTemplate;

import java.util.List;
import java.util.Map;

public interface TypeTemplateService {

    /**
     * 返回指定模板id的规格列表，包含规格选项
     * @param id
     * @return
     */
    public List<Map> findSpecList(Long id);
    /**
     * 查询所有
     * @return
     */
    public List<TbTypeTemplate> findAll();

    /**
     * 带条件带分页查询
     * @param pageNum
     * @param pageSize
     * @param tbTypeTemplate
     * @return
     */
    public PageResult findPage(int pageNum, int pageSize, TbTypeTemplate tbTypeTemplate);

    /**
     * 关联品牌和关联规格下拉框
     * @return
     */
    public Map selectOptionList();

    /**
     * 新建模板
     * @param tbTypeTemplate
     */
    public void add(TbTypeTemplate tbTypeTemplate);

    /**
     * 根据id获取实体
     * @param id
     * @return
     */
    public TbTypeTemplate findOne(Long id);

    /**
     * 更新模板
     * @param tbTypeTemplate
     */
    public void update(TbTypeTemplate tbTypeTemplate);

    /**
     * 批量删除
     * @param ids
     */
    public void delete(Long[] ids);

}
