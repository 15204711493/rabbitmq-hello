package com.atguigu.rabbitmq.eight;

import com.atguigu.rabbitmq.utils.RabbitMQUtils;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class direct_log {
    private static String exchange_name = "normal_exchange";
    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitMQUtils.getChannel();

        AMQP.BasicProperties basicProperties =
                new AMQP.BasicProperties().builder().expiration("10000").build();


        for (int i = 1; i <11 ; i++) {
              String message =  i+"消息";
              channel.basicPublish(exchange_name,"zhangsan",basicProperties,message.getBytes());
        }
    }
}
