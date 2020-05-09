package com.offcn.consumer;

import com.offcn.page.service.ItemPageService;
import com.offcn.util.MySerializeUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public class MessageConsumer implements MessageListener {

    @Autowired
    ItemPageService itemPageService;

    @Override
    public void onMessage(Message message) {
        Map map = (Map) MySerializeUtils.unserialize(message.getBody());
        Long[] ids = (Long[]) map.get("ids");
        String status = (String) map.get("status");
        if (status != null && status.equals("1")) {
            itemPageService.getItemHtml(ids);
        }
    }
}
