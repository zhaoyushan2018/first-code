package com.xiaoshan.erp.mq;

import com.xiaoshan.erp.service.PartsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.listener.SessionAwareMessageListener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * @author YushanZhao
 * @Date:2018/8/9
 */
public class FixOrderPartsNumListener implements SessionAwareMessageListener {

    Logger logger = LoggerFactory.getLogger(FixOrderPartsNumListener.class);

    @Autowired
    private PartsService partsService;

    @Override
    public void onMessage(Message message, Session session) throws JMSException {
        TextMessage textMessage = (TextMessage) message;

        try {
            //获得队列中的json数据
            String json = textMessage.getText();
            logger.info("从队列 接受到的json数据为:{}", json);
            //修改(减少) 配件库存
            partsService.updateInventory(json);

            //手动签收(接受数据成功,签收)
            textMessage.acknowledge();
        } catch (JMSException e) {
            e.printStackTrace();
            //重试机制 Redelivery(成功就签收,不成功就触发重试)
            session.recover();
        }

    }
}
