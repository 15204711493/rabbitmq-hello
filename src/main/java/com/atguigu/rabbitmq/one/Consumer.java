package com.atguigu.rabbitmq.one;

import com.atguigu.rabbitmq.utils.RabbitMQUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/*消费者 接受消息*/
public class Consumer {

    public static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws IOException, TimeoutException {
        //创建工厂

        //声明
        DeliverCallback deliverCallback = (consumerTag,message)->{
            System.out.println(new String(message.getBody()));
        };

        CancelCallback cancelCallback = c->{
            System.out.println("消息中断");
        };
        RabbitMQUtils.getChannel().basicConsume(QUEUE_NAME,true,deliverCallback,cancelCallback);
    }

}
