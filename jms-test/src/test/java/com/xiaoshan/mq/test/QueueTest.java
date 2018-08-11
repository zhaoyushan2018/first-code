package com.xiaoshan.mq.test;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.RedeliveryPolicy;
import org.junit.Test;
import org.springframework.jms.JmsException;

import javax.jms.*;
import java.io.IOException;

/**
 * @author YushanZhao
 * @Date:2018/8/8
 */
public class QueueTest {

    //重试机制
    @Test
    public void messageProduce() throws JMSException {
        Connection connection = null;
        Session session = null;
        MessageProducer producer = null;
        try {
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
            connection = connectionFactory.createConnection();
            connection.start();
            //手动提交 手动签收
            session = connection.createSession(true, Session.CLIENT_ACKNOWLEDGE);
            Destination destination = session.createQueue("oneQueue");
            producer = session.createProducer(destination);
            TextMessage textMessage = session.createTextMessage("hello producer 2");
            producer.send(textMessage);
            session.commit();
        } catch (JMSException e) {
            session.rollback();
        } finally {
            producer.close();
            session.close();
            connection.close();
        }



    }

    //自定义重试次数
    @Test
    public void messageConsumer() throws JMSException, IOException {
        /*ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");

        RedeliveryPolicy redeliveryPolicy = new RedeliveryPolicy();
        //设置重试次数
        redeliveryPolicy.setMaximumRedeliveries(3);
        //设置初次重试延迟时间,单位毫秒
        redeliveryPolicy.setInitialRedeliveryDelay(6000);
        //设置每次重试延迟时间,单位毫秒
        redeliveryPolicy.setRedeliveryDelay(3000);
        ((ActiveMQConnectionFactory) connectionFactory).setRedeliveryPolicy(redeliveryPolicy);*/
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");

        RedeliveryPolicy redeliveryPolicy = new RedeliveryPolicy();
        //设置重试次数
        redeliveryPolicy.setMaximumRedeliveries(3);
        //设置初次重试延迟时间,单位毫秒
        redeliveryPolicy.setInitialRedeliveryDelay(6000);
        //设置每次重试延迟时间,单位毫秒
        redeliveryPolicy.setRedeliveryDelay(3000);
        connectionFactory.setRedeliveryPolicy(redeliveryPolicy);

        Connection connection = connectionFactory.createConnection();
        connection.start();
        final Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
        Destination destination = session.createQueue("oneQueue");
        MessageConsumer consumer = session.createConsumer(destination);
        consumer.setMessageListener(new MessageListener() {
            public void onMessage(Message message) {
                try {
                    //消费者获取消息
                    TextMessage textMessage = (TextMessage) message;
                    System.out.println(textMessage.getText());
                    if("hello producer 2".equals(textMessage.getText())){
                        throw new JMSException("error");
                    }
                    //手动签收消息
                    textMessage.acknowledge();
                } catch (JMSException e) {
                    e.printStackTrace();
                    //重新接收消息(成功就签收,你成功就重试)
                    try {
                        session.recover();
                    } catch (JMSException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        System.in.read();
        consumer.close();
        session.close();
        connection.close();

    }


    //默认重试6次
    /* @Test
    public void messageConsumer() throws JMSException, IOException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        Connection connection = connectionFactory.createConnection();
        connection.start();
        final Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
        Destination destination = session.createQueue("oneQueue");
        MessageConsumer consumer = session.createConsumer(destination);
        consumer.setMessageListener(new MessageListener() {
            public void onMessage(Message message) {
                try {
                    //消费者获取消息
                    TextMessage textMessage = (TextMessage) message;
                    System.out.println(textMessage.getText());
                    if("hello producer 2".equals(textMessage.getText())){
                        throw new JMSException("error");
                    }
                    //手动签收消息
                    textMessage.acknowledge();
                } catch (JMSException e) {
                    e.printStackTrace();
                    //重新接收消息(成功就签收,你成功就重试)
                    try {
                        session.recover();
                    } catch (JMSException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        System.in.read();
        consumer.close();
        session.close();
        connection.close();

    }*/

    //异常
   /* @Test
    public void messageConsumer() throws JMSException, IOException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        Connection connection = connectionFactory.createConnection();
        connection.start();
        final Session session = connection.createSession(true, Session.CLIENT_ACKNOWLEDGE);
        Destination destination = session.createQueue("oneQueue");
        MessageConsumer consumer = session.createConsumer(destination);
        consumer.setMessageListener(new MessageListener() {
            public void onMessage(Message message) {
                //消费者接收消息
                TextMessage textMessage = (TextMessage) message;
                try {
                    System.out.println(textMessage.getText());
                    if("hello producer 2".equals(textMessage.getText())){
                        throw new JMSException("error");
                    }
                    //手动签收数据
                    textMessage.acknowledge();
                    session.commit();
                } catch (JMSException e) {
                    try {
                        session.rollback();
                    } catch (JMSException e1) {
                        e1.printStackTrace();
                    }
                    e.printStackTrace();
                }
            }
        });
        System.in.read();
        consumer.close();
        session.close();
        connection.close();

    }*/




/* - - - - - - - - -  - - - - - - - - - -  - - - - - - - - - -  - - - - - - - - - -  - - - - - - - - - -  - - - - - - - - - -  - - - - - - - - - -  - */
    //持久传输,非持久传输
    /*@Test
    public void messageProduce() throws JMSException {
        Connection connection = null;
        Session session = null;
        MessageProducer producer = null;
        try {
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
            connection = connectionFactory.createConnection();
            connection.start();
            //创建Session(param1:是否开启手动提交事务；接收者签收的模式：AUTO_ACKNOWLEDGE（自动签收）;CLIENT_ACKNOWLEDGE(手动签收))
            session = connection.createSession(true, Session.CLIENT_ACKNOWLEDGE);
            Destination destination = session.createQueue("oneQueue");
            producer = session.createProducer(destination);
            //非持久传输,MQ服务器重启后  1
            //producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            //持久传输,MQ服务器重启后,未消费的消息还会存在  2
            producer.setDeliveryMode(DeliveryMode.PERSISTENT);

            TextMessage textMessage = session.createTextMessage("hello producer 2");
            producer.send(textMessage);
            session.commit();

        } catch (JMSException e) {
            session.rollback();

        } finally {
            producer.close();
            session.close();
            connection.close();
        }
    }

    @Test
    public void messageConsumer() throws JMSException, IOException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        Connection connection = connectionFactory.createConnection();
        connection.start();
        final Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
        Destination destination = session.createQueue("oneQueue");
        MessageConsumer consumer = session.createConsumer(destination);
        consumer.setMessageListener(new MessageListener() {
            public void onMessage(Message message) {
                //消费者接收消息
                TextMessage textMessage = (TextMessage) message;
                try {
                    System.out.println(textMessage.getText());
                    //手动签收数据
                    textMessage.acknowledge();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
        System.in.read();
        consumer.close();
        session.close();
        connection.close();

    }*/



    //签收模式
    /*@Test
    public void messageProduce() throws JMSException {
        Connection connection = null;
        Session session = null;
        MessageProducer producer = null;
        try {
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
            connection = connectionFactory.createConnection();
            connection.start();
            //创建Session(param1:是否开启手动提交事务；接收者签收的模式：AUTO_ACKNOWLEDGE（自动签收）;CLIENT_ACKNOWLEDGE(手动签收))
            session = connection.createSession(true, Session.CLIENT_ACKNOWLEDGE);
            Destination destination = session.createQueue("oneQueue");
            producer = session.createProducer(destination);
            TextMessage textMessage = session.createTextMessage("hello producer 2");
            producer.send(textMessage);
            session.commit();

        } catch (JMSException e) {
            session.rollback();

        } finally {
            producer.close();
            session.close();
            connection.close();
        }
    }

    @Test
    public void messageConsumer() throws JMSException, IOException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        Connection connection = connectionFactory.createConnection();
        connection.start();
        final Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
        Destination destination = session.createQueue("oneQueue");
        MessageConsumer consumer = session.createConsumer(destination);
        consumer.setMessageListener(new MessageListener() {
            public void onMessage(Message message) {
                //消费者接收消息
                TextMessage textMessage = (TextMessage) message;
                try {
                    System.out.println(textMessage.getText());
                    //手动签收数据
                    textMessage.acknowledge();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
        System.in.read();
        consumer.close();
        session.close();
        connection.close();

    }*/


    //手动提交(异常时回滚,不出异常提交)
   /* @Test
    public void messageProduce() throws JMSException {
        Connection connection = null;
        Session session = null;
        MessageProducer producer = null;
        try {
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
            connection = connectionFactory.createConnection();
            connection.start();
            session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue("oneQueue");
            producer = session.createProducer(destination);
            TextMessage textMessage = session.createTextMessage("hello producer 2");
            producer.send(textMessage);
            session.commit();

        } catch (JMSException e) {
        session.rollback();

        } finally {
            producer.close();
            session.close();
            connection.close();
        }
    }

    @Test
    public void messageConsumer() throws JMSException, IOException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createQueue("oneQueue");
        MessageConsumer consumer = session.createConsumer(destination);
        consumer.setMessageListener(new MessageListener() {
            public void onMessage(Message message) {
                TextMessage textMessage = (TextMessage) message;
                try {
                    System.out.println(textMessage.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
        System.in.read();
        consumer.close();
        session.close();
        connection.close();

    }*/




    //测试队列(只能消费一次)
  /*  @Test
    public void messageProduce() throws JMSException, InterruptedException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createQueue("oneQueue");
        MessageProducer producer = session.createProducer(destination);

        int i = 0;
        while (true) {
            TextMessage textMessage = session.createTextMessage("hello producer " + i);
            producer.send(textMessage);

            //睡一秒
            Thread.sleep(1000);
            i++;
        }


    }

    @Test
    public void messageConsumer() throws JMSException, IOException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createQueue("oneQueue");
        MessageConsumer consumer = session.createConsumer(destination);
        consumer.setMessageListener(new MessageListener() {
            public void onMessage(Message message) {
                TextMessage textMessage = (TextMessage) message;
                try {
                    System.out.println("One--->" + textMessage.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });

        System.in.read();
        consumer.close();
        session.close();
        connection.close();

    }
    @Test
    public void messageConsumer1() throws JMSException, IOException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createQueue("oneQueue");
        MessageConsumer consumer = session.createConsumer(destination);
        consumer.setMessageListener(new MessageListener() {
            public void onMessage(Message message) {
                TextMessage textMessage = (TextMessage) message;
                try {
                    System.out.println("Two--->" + textMessage.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });

        System.in.read();
        consumer.close();
        session.close();
        connection.close();

    }
*/











  /*  @Test
    public void messageProduce() throws JMSException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createQueue("oneQueue");
        MessageProducer producer = session.createProducer(destination);
        TextMessage textMessage = session.createTextMessage("hello producer 2");
        producer.send(textMessage);

        producer.close();
        session.close();
        connection.close();

    }

     @Test
    public void messageConsumer() throws JMSException, IOException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createQueue("oneQueue");
        MessageConsumer consumer = session.createConsumer(destination);
        consumer.setMessageListener(new MessageListener() {
            public void onMessage(Message message) {
                TextMessage textMessage = (TextMessage) message;
                try {
                    System.out.println(textMessage.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });

        System.in.read();
        consumer.close();
        session.close();
        connection.close();

    }*/

















}
