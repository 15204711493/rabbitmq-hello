package com.atguigu.rabbitmq.eight;

import com.atguigu.rabbitmq.utils.RabbitMQUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class Consumer2 {



    private static final String d_q = "dead_queque";
    public static void main(String[] args) throws IOException, TimeoutException {

        Channel channel = RabbitMQUtils.getChannel();

        DeliverCallback deliverCallback =(a,b)->{
            System.out.println(new String(b.getBody()));
        };

        channel.basicConsume(d_q,true,deliverCallback, (CancelCallback) null);

    }
}
