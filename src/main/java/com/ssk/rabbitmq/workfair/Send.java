package com.ssk.rabbitmq.workfair;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.ssk.rabbitmq.util.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Send {

    private static final String QUEUE_NAME = "test_work_queue";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Connection connection = ConnectionUtils.connection();
        Channel channel = connection.createChannel();
        //持久化
        boolean durable = false;
        channel.queueDeclare(QUEUE_NAME,durable,false,false,null);

        /**
         * 每个消费者发送确认消息之前，消息队列不发送下一个消息到消费者，一次只处理一个消息
         *
         * 限制发给同一个消费者不得超过一条消息
         */
        int prefetchCount = 1;
        channel.basicQos(prefetchCount);
        for (int i = 0;i<50;i++){
            String msg = "hello "+i;
            System.out.println("[WQ ]send:"+msg);
            channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());
            Thread.sleep(i*20);
        }

        channel.close();
        connection.close();
    }

}
