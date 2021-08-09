package com.atguigu.rabbitmq.one;

import com.atguigu.rabbitmq.utils.RabbitMQUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/*生产者 发信息*/
public class producer {

    public static final String QUEUE_NAME = "hello";

    //发消息
    public static void main(String[] args) throws IOException, TimeoutException {

        //创建队列
        /*
        * 参数
        * 1队列名
        * 2是否持久化
        * 3消息是否共享默认false
        * 4是否自动删除
        * 5
        * */
       RabbitMQUtils.getChannel().queueDeclare(QUEUE_NAME,true,false,false,null);
        //发消息
        /*
        * 发送到哪个交换机*/
        String message = "hello world";
        RabbitMQUtils.getChannel().basicPublish("",QUEUE_NAME,null,message.getBytes());
        System.out.println("消息发生完毕");

    }
}
