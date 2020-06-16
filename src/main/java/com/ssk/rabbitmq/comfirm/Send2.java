package com.ssk.rabbitmq.comfirm;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.ssk.rabbitmq.util.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Send2 {

    private static final String QUEUE_NAME = "test_queue_confirm2";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Connection connection = ConnectionUtils.connection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        channel.confirmSelect();
        String msgString = "hello confirm message!";
        for (int i = 0 ; i < 10 ; i++){
            channel.basicPublish("",QUEUE_NAME,null,msgString.getBytes());
        }


        if (!channel.waitForConfirms()){
            System.out.println("message send failed");
        }else{
            System.out.println("message send ok");
        }
        channel.close();
        connection.close();


    }
}
