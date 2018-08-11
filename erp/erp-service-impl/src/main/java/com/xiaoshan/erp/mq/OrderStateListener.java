package com.xiaoshan.erp.mq;

import com.xiaoshan.erp.service.OrderService;
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
public class OrderStateListener implements SessionAwareMessageListener {

    private Logger logger = LoggerFactory.getLogger(OrderStateListener.class);

    @Autowired
    private OrderService orderService;

    @Override
    public void onMessage(Message message, Session session) throws JMSException {
        TextMessage textMessage = (TextMessage) message;
        try {
            String json = textMessage.getText();
            logger.info("接受到的json数据为:{}",json);
            orderService.editOrderState(json);

            //手动签收
            textMessage.acknowledge();
        } catch (JMSException e) {
            e.printStackTrace();
            //重试机制 Redelivery (成功就签收,不成功就重试)
            session.recover();
        }
    }










}
