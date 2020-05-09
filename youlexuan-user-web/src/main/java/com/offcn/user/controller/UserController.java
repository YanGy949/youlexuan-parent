package com.offcn.user.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.offcn.pojo.TbUser;
import com.offcn.user.service.UserService;
import com.offcn.util.MsgCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    RedisTemplate redisTemplate;

    @Reference
    UserService userService;


    @RequestMapping("/register")
    public Map register(@RequestBody TbUser tbUser, String msgCode) {

        Map map = new HashMap();
        String redis_msgCode = redisTemplate.boundHashOps("MsgCode").get(tbUser.getPhone()) + "";
        if (redis_msgCode != null && redis_msgCode.equals(msgCode)) {
            userService.add(tbUser);
            map.put("code", true);
            map.put("message", "注册成功");
        } else {
            map.put("code", false);
            map.put("message", "验证码错误");
        }
        return map;
    }

    @RequestMapping("/sendMsgCode")
    public Map sendMsgCode(String phone) {

        Map map = new HashMap();

        List<TbUser> users = userService.findByPhone(phone);
        if (users.size() > 0) {
            map.put("message", "该号码已注册");
            map.put("code", false);
        } else {
            String msgCode = MsgCodeUtil.createMsgCode();
            boolean b = MsgCodeUtil.sendMsgCode(phone, msgCode);
            if (b) {
                redisTemplate.boundHashOps("MsgCode").put(phone, msgCode);
                map.put("message", "验证码发送成功");
                map.put("code", true);
            } else {
                map.put("message", "验证码发送失败");
                map.put("code", false);
            }
        }
        return map;

    }


}
