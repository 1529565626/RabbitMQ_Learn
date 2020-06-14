package com.ssk.rabbitmq.simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.ssk.rabbitmq.util.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Send {

    private static final String QUEUE_NAME = "test_simple_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        //获取一个链接
        Connection connection = ConnectionUtils.connection();
        //从链接中获取一个通道
        Channel channel = connection.createChannel();
        //创建声明队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        String msg = "hello simole !";

        channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());

        System.out.println("--send msg:"+msg);

        channel.close();
        connection.close();

    }
}
