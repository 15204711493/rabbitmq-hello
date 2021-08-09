package com.atguigu.rabbitmq.three;

import com.atguigu.rabbitmq.utils.RabbitMQUtils;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Work03 {

    public static final String task_queue_name = "ack_queue";

    public static void main(String[] args) throws IOException, TimeoutException {

        Channel channel = RabbitMQUtils.getChannel();
        System.out.println("c1等待处理消息时间短");
        channel.confirmSelect();


        DeliverCallback deliverCallback = (consumerTag, message)->{
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(new String(message.getBody()));

            //设置不公平分发
            channel.basicQos(1);
            //手动应答
            channel.basicAck(message.getEnvelope().getDeliveryTag(),false);

        };


        channel.basicConsume(task_queue_name,false,deliverCallback,c->{
            System.out.println(c+"消费者取消消费接口调用");
        });
    }
}
