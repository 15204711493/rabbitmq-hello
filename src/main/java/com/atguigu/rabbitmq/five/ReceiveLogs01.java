package com.atguigu.rabbitmq.five;

import com.atguigu.rabbitmq.utils.RabbitMQUtils;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/*消息接收*/
public class ReceiveLogs01 {

    //交换机的名称
    private static String exchange_name = "logs";
    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitMQUtils.getChannel();
        //声明一个交换机
        channel.exchangeDeclare(exchange_name,"fanout");
        String queue = channel.queueDeclare().getQueue();
        channel.queueBind(queue,exchange_name,"");
        System.out.println("等待接收消息...");

        DeliverCallback deliverCallback = (consumerTag, message)->{
            System.out.println(new String(message.getBody()));
        };

        channel.basicConsume(queue,true,deliverCallback, (CancelCallback) null);
    }
}
