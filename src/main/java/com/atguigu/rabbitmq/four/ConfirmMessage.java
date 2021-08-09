package com.atguigu.rabbitmq.four;

import com.atguigu.rabbitmq.utils.RabbitMQUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmCallback;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.TimeoutException;

public class ConfirmMessage {

    public static final int message_count = 1000;

    public static void main(String[] args) throws InterruptedException, TimeoutException, IOException {
        //单个确认
        //publishOne();
        //批量
        //publishMany();
        //异步
        publishAsync();
    }


    public static void publishOne() throws IOException, TimeoutException, InterruptedException {
        Channel channel = RabbitMQUtils.getChannel();
        channel.confirmSelect();
        String queueName = UUID.randomUUID().toString();
        channel.queueDeclare(queueName, true, false, false, null);
        //开始时间
        long a = System.currentTimeMillis();

        for (int i = 0; i < message_count; i++) {
            String message = i + "";
            channel.basicPublish("", queueName, null, message.getBytes());
            boolean b = channel.waitForConfirms();
            if (b) {
                System.out.println("ok");
            }

        }
        long b = System.currentTimeMillis();
        System.out.println("发布1000个单个消息耗时" + (b - a) + "毫秒");


    }

    public static void publishMany() throws IOException, TimeoutException, InterruptedException {
        Channel channel = RabbitMQUtils.getChannel();

        String queueName = UUID.randomUUID().toString();
        channel.queueDeclare(queueName, true, false, false, null);
        channel.confirmSelect();
        //开始时间
        long a = System.currentTimeMillis();
        for (int i = 0; i < message_count; i++) {
            String message = i + "";
            channel.basicPublish("", queueName, null, message.getBytes());
            if (i % 100 == 0) {
                boolean b = channel.waitForConfirms();
            }
        }
        long b = System.currentTimeMillis();
        System.out.println("发布1000个单个消息耗时" + (b - a) + "毫秒");
    }

    //异步发布确认
    public static void publishAsync()throws IOException, TimeoutException, InterruptedException{
        Channel channel = RabbitMQUtils.getChannel();
        String queueName = UUID.randomUUID().toString();
        channel.queueDeclare(queueName, true, false, false, null);
        channel.confirmSelect();

        ConcurrentSkipListMap<Long,String> concurrentSkipListMap =
                new ConcurrentSkipListMap<>();

        ConfirmCallback confirmCallback = (a,b)->{
            if(b){
                ConcurrentNavigableMap<Long, String> longStringConcurrentNavigableMap =
                        concurrentSkipListMap.headMap(a);
                longStringConcurrentNavigableMap.clear();
            }else {
                concurrentSkipListMap.remove(a);
            }

            System.out.println("确认消息"+a);
        };

        ConfirmCallback noConfirmCallback = (a,b)->{
            String s = concurrentSkipListMap.get(a);
            System.out.println(s+"未确认消息"+a);

        };

        //消息监听器
        channel.addConfirmListener(confirmCallback,noConfirmCallback);

        //开始时间
        long a = System.currentTimeMillis();

        for (int i = 0; i < message_count; i++) {
            String message =  "消息" +i;
            channel.basicPublish("", queueName, null, message.getBytes());
            concurrentSkipListMap.put(channel.getNextPublishSeqNo(),message);

        }
        long b = System.currentTimeMillis();
        System.out.println("异步发布1000个单个消息耗时" + (b - a) + "毫秒");
    }




}
