package com.offcn.sellergoods.controller;

import org.springframework.security.core.context.SecurityContextHolder;
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
public class LoginController {

    @RequestMapping("/getName")
    public Map getName(@CookieValue(value = "lastLoginTime", required = false) String lastLoginTime, HttpServletResponse response) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        Map map = new HashMap();
        map.put("name",name);
        Jedis jedis = new Jedis("192.168.188.128", 6379);
        jedis.auth("123");
        String managerWebLastLogintime = jedis.hget("managerWebLastLogintime", name);
        if (managerWebLastLogintime == null) {
            Map<String, String> hash = new HashMap<>();
            hash.put(name, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            jedis.hmset("managerWebLastLogintime", hash);
            map.put("lastLoginTime", "当前为首次登录");
            return map;
        } else {
            if (lastLoginTime != null) {
                map.put("lastLoginTime", lastLoginTime);
                return map;
            } else {
                Cookie cookie = new Cookie("lastLoginTime", managerWebLastLogintime);
                cookie.setMaxAge(-1);
                cookie.setPath("/");
                response.addCookie(cookie);
                map.put("lastLoginTime", managerWebLastLogintime);
                Map<String, String> hash = new HashMap<>();
                hash.put(name, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                jedis.hmset("managerWebLastLogintime", hash);
                return map;
            }
        }
    }
}
