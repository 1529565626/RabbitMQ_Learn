package com.ssk.rabbitmq.comfirm;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import com.ssk.rabbitmq.util.ConnectionUtils;

import java.io.IOException;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.TimeoutException;

public class Send3 {

    private static final String QUEUE_NAME = "test_queue_confirm3";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Connection connection = ConnectionUtils.connection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        channel.confirmSelect();
        String msgString = "hello confirm message!";

        final SortedSet<Long> confirmSet = Collections.synchronizedSortedSet(new TreeSet<Long>());
        channel.addConfirmListener(new ConfirmListener() {
            //没有问题的handleAck
            @Override
            public void handleAck(long deliverTag, boolean multiple) throws IOException {
                if (multiple){
                    System.out.println("---handleAck---multiple");
                    confirmSet.headSet(deliverTag+1).clear();
                }else{
                    System.out.println("---handleAck---multiple false");
                    confirmSet.remove(deliverTag);
                }
            }
            @Override
            public void handleNack(long deliverTag, boolean multiple) throws IOException {
                if (multiple){
                    System.out.println("---handleAck---multiple");
                    confirmSet.headSet(deliverTag+1).clear();
                }else{
                    System.out.println("---handleAck---multiple false");
                    confirmSet.remove(deliverTag);
                }
            }
        });

        String msgStr = "synchronized msg";

        while (true){
            long seqNo = channel.getNextPublishSeqNo();
            channel.basicPublish("",QUEUE_NAME,null,msgStr.getBytes());
            confirmSet.add(seqNo);
        }



    }
}
