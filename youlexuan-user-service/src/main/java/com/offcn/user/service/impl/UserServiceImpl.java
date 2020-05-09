package com.offcn.user.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.offcn.entity.PageResult;
import com.offcn.mapper.TbUserMapper;
import com.offcn.pojo.TbUser;
import com.offcn.pojo.TbUserExample;
import com.offcn.user.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private TbUserMapper tbUserMapper;

    @Override
    public List<TbUser> findByPhone(String phone) {
        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        criteria.andPhoneEqualTo(phone);
        List<TbUser> tbUsers = tbUserMapper.selectByExample(example);
        return tbUsers;
    }

    @Override
    public PageResult findAll(TbUser tbUser, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        TbUserExample tbUserExample = new TbUserExample();
        TbUserExample.Criteria criteria = tbUserExample.createCriteria();
        if (tbUser != null) {
            if (tbUser.getUsername() != null && tbUser.getUsername().length() > 0) {
                criteria.andUsernameLike("%" + tbUser.getUsername() + "%");
            }
            if (tbUser.getSourceType() != null && tbUser.getSourceType().length() > 0) {
                criteria.andSourceTypeEqualTo(tbUser.getSourceType());
            }
        }
        Page<TbUser> page = (Page<TbUser>) tbUserMapper.selectByExample(tbUserExample);
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public void add(TbUser tbUser) {
        tbUser.setUpdated(new Date());
        tbUser.setCreated(new Date());
        String password = DigestUtils.md5Hex(tbUser.getPassword());
        tbUser.setPassword(password);
        tbUserMapper.insert(tbUser);
    }

    @Override
    public void delete(Long[] ids) {
        for (Long id:ids) {
            tbUserMapper.deleteByPrimaryKey(id);
        }
    }

    @Override
    public TbUser findOne(Long id) {
        return tbUserMapper.selectByPrimaryKey(id);
    }

    @Override
    public void update(TbUser tbUser) {
        tbUserMapper.updateByPrimaryKey(tbUser);
    }


    public static void main(String[] args) {

        String password = DigestUtils.md5Hex("123456");
        System.out.println("数据库中的密码：" + password);

        String password1 = "123456";
        if (password.equals(DigestUtils.md5Hex(password1))) {
            System.out.println("页面获取的密码加密后：" + DigestUtils.md5Hex(password1));
            System.out.println("密码正确");
        } else {
            System.out.println("页面获取的密码加密后：" + DigestUtils.md5Hex(password1));
            System.out.println("密码错误");
        }
    }
}
