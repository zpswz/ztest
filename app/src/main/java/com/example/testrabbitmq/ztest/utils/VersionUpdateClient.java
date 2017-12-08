package com.example.testrabbitmq.ztest.utils;

import android.util.Log;

import com.blankj.utilcode.util.LogUtils;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 *
 * @author Administrator
 * @date 2016/12/29
 */

public class VersionUpdateClient {
    private  long updataTime;
    public static void startVersionUpdateMonitor(){
        LogUtils.d("startVersionUpdateMonitor","进入消息推送  ======");
        try {
            Connection connection= InformationPushUtils.getConnection();
            final Channel channel = connection.createChannel();
            channel.basicQos(1);

            // 创建一个非持久的、唯一的且自动删除的队列
            String queueName = channel.queueDeclare().getQueue();
            // 为转发器指定队列，设置binding
            channel.queueBind(queueName, InformationPushUtils.UPDATE_VERSION, "NF160813628100"+".#");
            channel.queueBind(queueName, InformationPushUtils.UPDATE_VERSION, "test");
            // 指定接收者，第二个参数为自动应答，无需手动应答
            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                        throws IOException {
                    String message = new String(body, "UTF-8");
//                    JSONObject jsonObject=null;
//                    try {
//                        jsonObject=new JSONObject(message);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
                    channel.basicAck(envelope.getDeliveryTag(), false);
                    System.out.println("后台消息来了======" + message + "/"+envelope.getRoutingKey());
                    Log.d("aaaaaaa", "startVersionUpdateMonitor" + message+"/"+envelope.getRoutingKey());
                    LogUtils.d("startVersionUpdateMonitor","后台消息来了======",message,envelope.getRoutingKey());
                }
            };
            channel.basicConsume(queueName, false, consumer);
        }catch (Exception e){
            e.printStackTrace();
            LogUtils.d("startVersionUpdateMonitor","异常======");
        }
    }
}
