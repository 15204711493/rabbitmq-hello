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

public class Consumer {

    private static final String n_n = "normal_exchange";
    private static final String d_n = "dead_exchange";
    private static final String n_q = "normal_queque";
    private static final String d_q = "dead_queque";
    public static void main(String[] args) throws IOException, TimeoutException {

        Channel channel = RabbitMQUtils.getChannel();

        channel.exchangeDeclare(n_n, BuiltinExchangeType.DIRECT);
        channel.exchangeDeclare(d_n, BuiltinExchangeType.DIRECT);

        Map<String, Object> c = new HashMap<>();
        c.put("x-dead-letter-exchange",d_n);
        c.put("x-dead-letter-routing-key","lisi");

        channel.queueDeclare(n_q,false,false,false,c);

        channel.queueDeclare(d_q,false,false,false,null);

        channel.queueBind(n_q,n_n,"zhangsan");
        channel.queueBind(d_q,d_n,"lisi");

        DeliverCallback deliverCallback =(a,b)->{
            System.out.println(new String(b.getBody()));
        };

        channel.basicConsume(n_q,true,deliverCallback, (CancelCallback) null);

    }
}
