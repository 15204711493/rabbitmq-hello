package com.atguigu.rabbitmq.seven;

import com.atguigu.rabbitmq.utils.RabbitMQUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ReceiveLogsDirect02 {

     private static String e_n = "topic_logs";
    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitMQUtils.getChannel();
        //声明一个交换机
        channel.exchangeDeclare(e_n, BuiltinExchangeType.TOPIC);
        //声明队列
        channel.queueDeclare("q1",false,false,false,null);
        channel.queueBind("q1",e_n,"*.orange.*");
        System.out.println("等待接收消息...");

        DeliverCallback deliverCallback = (consumerTag, message)->{
            System.out.println(new String(message.getBody()));
        };

        channel.basicConsume("q1",true,deliverCallback, (CancelCallback) null);
    }

    }


