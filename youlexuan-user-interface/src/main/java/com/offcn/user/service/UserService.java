package com.offcn.user.service;

import com.offcn.entity.PageResult;
import com.offcn.pojo.TbUser;

import java.util.List;

public interface UserService {
    /**
     * 通过号码查询有无对应的用户【判断该号码是否已经注册过】
     * @param phone
     * @return
     */
    public List<TbUser> findByPhone(String phone);

    /**
     * 带条件的分页查询
     * @param tbUser
     * @param pageNum
     * @param pageSize
     * @return
     */
    public PageResult findAll(TbUser tbUser, int pageNum, int pageSize);

    /**
     * 新增用户
     * @param tbUser
     */
    public void add(TbUser tbUser);

    /**
     * 删除用户
     * @param ids
     */
    public void delete(Long[] ids);

    /**
     * 根据id获取实体
     * @param id
     * @return
     */
    public TbUser findOne(Long id);

    /**
     * 更新用户
     * @param tbUser
     */
    public void update(TbUser tbUser);

}
