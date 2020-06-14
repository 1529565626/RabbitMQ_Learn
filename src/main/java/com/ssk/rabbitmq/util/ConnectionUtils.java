package com.ssk.rabbitmq.util;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ConnectionUtils {


    /**
     * 获取MQ的链接
     * @return
     */
    public static Connection connection() throws IOException, TimeoutException {
        //定义一个链接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //设置服务地址
        factory.setHost("192.168.91.128");
        //设置端口
        factory.setPort(5672);
        //vhost
        factory.setVirtualHost("/vhost_mmr");
        //用户名
        factory.setUsername("user_mmr");
        //密码
        factory.setPassword("123");

        return factory.newConnection();
    }
}
