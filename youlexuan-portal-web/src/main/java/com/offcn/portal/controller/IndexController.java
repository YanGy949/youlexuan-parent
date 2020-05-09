package com.offcn.portal.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.offcn.pojo.TbItemCat;
import com.offcn.sellergoods.service.ItemCatService;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/index")
public class IndexController {

    @Reference
    private ItemCatService itemCatService;

    @RequestMapping("/findItemCats")
    public List<TbItemCat> findItemCats() {
        return itemCatService.findItemCats();
    }

    @RequestMapping("/saveKeyWords")
    public Map saveKeyWords(@RequestBody Map map, @CookieValue(value = "keywords", required = false) String keyWords, HttpServletResponse response) {
        Map keys = new HashMap();
        String keyword = (String) map.get("keywords");
        String keywords = null;
        try {
            keywords = URLEncoder.encode(keyword, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if (keyWords != null) {
            if (keyWords.split(",").length <= 5) {
                try {
                    keyWords = URLEncoder.encode(keyWords, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Cookie cookie = new Cookie("keywords", keyWords + "," + keywords);
                cookie.setMaxAge(-1);
                cookie.setPath("/");
                try {
                    System.out.println(URLDecoder.decode(keyWords, "UTF-8") + "," + URLDecoder.decode(keywords, "UTF-8"));
                    response.addCookie(cookie);
                    keys.put("keys", URLDecoder.decode(keyWords, "UTF-8") + "," + URLDecoder.decode(keywords, "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

        } else {
            Cookie cookie = new Cookie("keywords", keywords);
            cookie.setMaxAge(-1);
            cookie.setPath("/");
            try {
                System.out.println(URLDecoder.decode(keywords, "UTF-8"));
                keys.put("keys", URLDecoder.decode(keywords, "UTF-8"));
                response.addCookie(cookie);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return keys;
    }

}
