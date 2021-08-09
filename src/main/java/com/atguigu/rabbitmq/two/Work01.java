package com.atguigu.rabbitmq.two;

import com.atguigu.rabbitmq.utils.RabbitMQUtils;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/*工作线程1*/
public class Work01 {

    public static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitMQUtils.getChannel();

        //声明
        DeliverCallback deliverCallback = (consumerTag, message)->{
            System.out.println("接受到的消息"+new String(message.getBody()));
        };

        CancelCallback cancelCallback = c->{
            System.out.println(c+"消息中断");
        };
        System.out.println("c2 .....");
        channel.basicConsume(QUEUE_NAME,true,deliverCallback,cancelCallback);
    }
}
