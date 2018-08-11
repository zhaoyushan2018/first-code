package com.xiaoshan.erp.mq;

import com.xiaoshan.erp.service.FixOrderService;
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
public class FixOrderListener implements SessionAwareMessageListener {

    private Logger logger = LoggerFactory.getLogger(FixOrderListener.class);

    @Autowired
    private FixOrderService fixOrderService;

    @Override
    public void onMessage(Message message, Session session) throws JMSException {
        TextMessage textMessage = (TextMessage) message;

        try {
            //获得队列中的json数据
            String json = textMessage.getText();
            logger.info("队列接收到的json数据:{}",json);
            fixOrderService.createFixOrder(json);

            //手动签收
            textMessage.acknowledge();
        } catch (JMSException e) {
            e.printStackTrace();
            //成功就签收,不成功就重试
            session.recover();
        }

    }
}
