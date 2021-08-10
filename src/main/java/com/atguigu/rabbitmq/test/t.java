package com.atguigu.rabbitmq.test;

import java.util.HashMap;
import java.util.Map;

public class t {

    public static void main(String[] args) {

        Map<String, String> map = new HashMap<>();
        map.put("name","jack");


        for (Map.Entry<String, String> entry : map.entrySet()) {

          //  System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());

            System.out.println(entry);

        }
    }
}