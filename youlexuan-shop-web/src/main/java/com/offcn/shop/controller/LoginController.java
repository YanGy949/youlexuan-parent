package com.offcn.shop.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/login")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= "classpath*:spring/spring-redis.xml")
public class LoginController {

    @Autowired
    RedisTemplate redisTemplate;

    @RequestMapping("/getName")
    public Map getName(@CookieValue(value = "lastLoginTime", required = false) String lastTime, HttpServletResponse response) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        Map map = new HashMap();
        map.put("sellerId", name);
        Jedis jedis = new Jedis("192.168.188.128", 6379);
        jedis.auth("123");
        String showWebLastLoginTime = jedis.hget("showWebLastLoginTime", name);
        if (showWebLastLoginTime == null) {
            map.put("lastLoginTime", "当前为首次登录");
            Map<String, String> hash = new HashMap<>();
            hash.put(name, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            jedis.hmset("showWebLastLoginTime", hash);
            return map;
        } else {
            if (lastTime != null && !lastTime.equals("")) {
                map.put("lastLoginTime",lastTime);
                return map;
            }else{
                Cookie cookie = new Cookie("lastLoginTime", showWebLastLoginTime);
                cookie.setMaxAge(-1);
                cookie.setPath("/");
                response.addCookie(cookie);
                Map<String, String> hash = new HashMap<>();
                hash.put(name, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                jedis.hmset("showWebLastLoginTime", hash);
                map.put("lastLoginTime", showWebLastLoginTime);
                return map;
            }
        }
    }

    @Test
    public void test(){
        redisTemplate.boundHashOps("myHash").put("name","offcn");
        Object o = redisTemplate.boundHashOps("myHash").get("name");
        System.out.println(o);
    }
}
