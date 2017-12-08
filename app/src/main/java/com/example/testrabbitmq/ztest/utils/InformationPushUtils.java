package com.example.testrabbitmq.ztest.utils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by Administrator on 2017/1/17.
 */

public class InformationPushUtils {
    private final static String host = "123.147.223.119";
    public static final String UPDATE_VERSION = "medicine";
    public static final Connection getConnection() {
        Connection connection = null;
        try {
            ConnectionFactory connectionFactory = new ConnectionFactory();
            connectionFactory.setHost(host);
            connectionFactory.setPort(5672);
            connectionFactory.setUsername("machine");
            connectionFactory.setPassword("hykd@2018");
           connectionFactory.setVirtualHost("medicine");
            connection = connectionFactory.newConnection();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static void close(Channel channel, Connection connection) {
        try {
            if (null != channel) {
                channel.close();
            }
            if (connection != channel) {
                connection.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}
