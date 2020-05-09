package com.offcn.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.offcn.entity.PageResult;
import com.offcn.entity.SpecificationVO;
import com.offcn.mapper.TbSpecificationMapper;
import com.offcn.mapper.TbSpecificationOptionMapper;
import com.offcn.pojo.TbSpecification;
import com.offcn.pojo.TbSpecificationExample;
import com.offcn.pojo.TbSpecificationOption;
import com.offcn.pojo.TbSpecificationOptionExample;
import com.offcn.sellergoods.service.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class SpecificationServiceImpl implements SpecificationService {

    @Autowired
    TbSpecificationMapper tbSpecificationMapper;

    @Autowired
    TbSpecificationOptionMapper optionMapper;


    @Override
    public List<TbSpecification> findAll() {
        return tbSpecificationMapper.selectByExample(null);
    }

    @Override
    public PageResult findPage(int pageNum, int pageSize, TbSpecification tbSpecification) {
        PageHelper.startPage(pageNum, pageSize);
        TbSpecificationExample tbSpecificationExample = new TbSpecificationExample();
        TbSpecificationExample.Criteria or = tbSpecificationExample.or();
        if (tbSpecification != null && tbSpecification.getSpecName() != null) {
            or.andSpecNameLike("%" + tbSpecification.getSpecName() + "%");
        }
        Page<TbSpecification> page = (Page<TbSpecification>) tbSpecificationMapper.selectByExample(tbSpecificationExample);

        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public void add(SpecificationVO specificationVO) {
        //规格表
        TbSpecification specification = specificationVO.getSpecification();
        //添加spcification要返回id，给规格选项的外键

        tbSpecificationMapper.insert(specification);

        Long id = specification.getId();//返回的主键

        List<TbSpecificationOption> list = specificationVO.getSpecificationOption();

        for (TbSpecificationOption option : list) {
            option.setSpecId(id);
            optionMapper.insert(option);
        }

    }

    @Override
    public SpecificationVO findOne(Long id) {
        //根据id获取规格
        TbSpecification tbSpecification = tbSpecificationMapper.selectByPrimaryKey(id);
        //查询规格列表
        TbSpecificationOptionExample example = new TbSpecificationOptionExample();
        TbSpecificationOptionExample.Criteria criteria = example.createCriteria();
        criteria.andSpecIdEqualTo(id);
        List<TbSpecificationOption> options = optionMapper.selectByExample(example);
        SpecificationVO specificationVO = new SpecificationVO();
        specificationVO.setSpecification(tbSpecification);
        specificationVO.setSpecificationOption(options);
        return specificationVO;
    }

    @Override
    public void update(SpecificationVO specificationVO) {

        //保存修改的规格
        tbSpecificationMapper.updateByPrimaryKey(specificationVO.getSpecification());

        //根据specid删除原有的规格选项,之后再重新循环插入规格选项
        TbSpecificationOptionExample example = new TbSpecificationOptionExample();
        TbSpecificationOptionExample.Criteria or = example.or();
        or.andSpecIdEqualTo(specificationVO.getSpecification().getId());//指定id为删除条件
        optionMapper.deleteByExample(example);//删除

        for (TbSpecificationOption option : specificationVO.getSpecificationOption()) {
            option.setSpecId(specificationVO.getSpecification().getId());
            optionMapper.insert(option);
        }
    }

    @Override
    public void delete(Long[] ids) {
        for (Long id:ids) {
            tbSpecificationMapper.deleteByPrimaryKey(id);//删除规格

            //删除规格选项
            TbSpecificationOptionExample example = new TbSpecificationOptionExample();
            TbSpecificationOptionExample.Criteria or = example.or();
            or.andSpecIdEqualTo(id);//规格id为删除条件
            optionMapper.deleteByExample(example);//删除规格选项
        }
    }
}

