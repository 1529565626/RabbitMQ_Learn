package com.ssk.rabbitmq.topic;

import com.rabbitmq.client.*;
import com.ssk.rabbitmq.util.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Send {


    private static final String EXCHANGE_NAME = "test_exchange_topic";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.connection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME,"topic");
        String msgString = "商品...";
        channel.basicPublish(EXCHANGE_NAME,"good.delete",null,msgString.getBytes());
        System.out.println("---send :"+msgString);

        channel.close();
        connection.close();

    }

}
