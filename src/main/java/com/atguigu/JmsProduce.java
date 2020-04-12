package com.atguigu;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import static javax.jms.Session.AUTO_ACKNOWLEDGE;

/**
 * @author 顾书才
 * @version V1.0
 * @Package com.atguigu
 * @date 2020/4/12 0012 13:17
 * @Description 消息生产者
 */
public class JmsProduce {
    private static final String ACTIVEMQ_URL = "tcp://192.168.70.141:61616";
    private static final String QUEUE_NAME = "queue01";

    public static void main(String[] args) throws JMSException {
        //1.创建连接工厂，按照给定的URL，采用默认的用户名密码
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        //2.通过连接工厂,获得connection并启动访问
        Connection connection = factory.createConnection();
        connection.start();
        //3.创建会话session
        //两个参数transacted=事务,acknowledgeMode=确认模式(签收)
        Session session = connection.createSession(false, AUTO_ACKNOWLEDGE);
        //4.创建目的地(具体是队列queue还是主题topic)
        Queue queue = session.createQueue(QUEUE_NAME);
        //5.创建消息的生产者
        MessageProducer producer = session.createProducer(queue);
        for (int i = 0; i < 3; i++) {
            //7.创建消息
            TextMessage textMessage = session.createTextMessage("msg---hello" + i);//理解为一个字符串
            //8.通过messageProducer发送给MQ队列
            producer.send(textMessage);
        }
        //9.关闭资源
        producer.close();
        session.close();
        System.out.println("****消息发布到MQ队列完成");

    }
}
