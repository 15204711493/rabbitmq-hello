package com.atguigu.rabbitmq.seven;

import com.atguigu.rabbitmq.utils.RabbitMQUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ReceiveLogsDirect01 {

     private static String e_n = "topic_logs";
    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitMQUtils.getChannel();
        //声明一个交换机
        channel.exchangeDeclare(e_n, BuiltinExchangeType.TOPIC);
        //声明队列
        channel.queueDeclare("q2",false,false,false,null);
        channel.queueBind("q2",e_n,"*.*.rabbit");
       // channel.queueBind("q2",e_n,"lazy.#");
        System.out.println("等待接收消息...");

        DeliverCallback deliverCallback = (consumerTag, message)->{
            System.out.println(new String(message.getBody()));
        };

        channel.basicConsume("q2",true,deliverCallback, (CancelCallback) null);
    }

    }


