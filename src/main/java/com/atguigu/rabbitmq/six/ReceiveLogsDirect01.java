package com.atguigu.rabbitmq.six;

import com.atguigu.rabbitmq.utils.RabbitMQUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ReceiveLogsDirect01 {

     private static String e_n = "direct_logs";
    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitMQUtils.getChannel();
        //声明一个交换机
        channel.exchangeDeclare(e_n, BuiltinExchangeType.DIRECT);
        //声明队列
        channel.queueDeclare("consloe",false,false,false,null);
        channel.queueBind("consloe",e_n,"info");
        channel.queueBind("consloe",e_n,"warning");
        System.out.println("等待接收消息...");

        DeliverCallback deliverCallback = (consumerTag, message)->{
            System.out.println(new String(message.getBody()));
        };

        channel.basicConsume("consloe",true,deliverCallback, (CancelCallback) null);
    }

    }


