package com.offcn.sellergoods.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.offcn.entity.PageResult;
import com.offcn.entity.Result;
import com.offcn.pojo.TbUser;
import com.offcn.user.service.UserService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/user")
public class UserController {

    @Reference
    private UserService userService;

    @RequestMapping("/findAll")
    public PageResult findAll(@RequestBody TbUser tbUser, int pageNum, int pageSize){
        return userService.findAll(tbUser,pageNum,pageSize);
    }

    @RequestMapping("/add")
    public Result add(@RequestBody TbUser tbUser){
        try {
            Date date = new Date();
            tbUser.setCreated(date);
            tbUser.setUpdated(date);
            userService.add(tbUser);
            return new Result(true,"新建成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"新建失败");
        }
    }

    @RequestMapping("/findOne")
    public TbUser findOne(Long id){
        return userService.findOne(id);
    }

    @RequestMapping("/update")
    public Result update(@RequestBody TbUser tbUser){
        try {
            userService.update(tbUser);
            return new Result(true,"修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"修改失败");
        }
    }

    @RequestMapping("/delete")
    public Result delete(Long[] ids){
        try {
            userService.delete(ids);
            return new Result(true,"删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"删除失败");
        }
    }

}
