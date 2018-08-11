package com.xiaoshan.mq.spring;

import org.apache.activemq.command.ActiveMQQueue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.io.IOException;

/**
 * @author YushanZhao
 * @Date:2018/8/8
 */
//读取spring配置文件
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:jms-spring-queue.xml")
public class SpringQueue {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Test
    public void springQueueTest(){
        jmsTemplate.send(new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage("spring quue mq-1");
            }
        });
    }
    @Test
    public void springQueueTest2(){
        jmsTemplate.send("twoQueue",new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage("spring quue mq-1");
            }
        });
    }

    @Test
    public void springQueueTest3(){
        Destination destination = new ActiveMQQueue("threeQueue");
        jmsTemplate.send(destination,new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage("spring quue mq-1");
            }
        });
    }

    @Test
    public void readMessage() throws IOException {
        System.out.println("spring start......");
        System.in.read();
    }

}
