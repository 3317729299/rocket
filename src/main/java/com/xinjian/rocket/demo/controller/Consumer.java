package com.xinjian.rocket.demo.controller;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

//接收请求 验证  点击信息展现到页面上
//登录发送请求 用户在线人数
// 
public class Consumer {

    public  static void   StringToJsonArray(String bytes) {

        String  content = "{\"kunming\",\"gender\":\"0\",\"age\":\"28\",\"username\":\"tanrundong\",\"location\":\"yunnanxinjian\",\"accountId\":\"9990395\"}";
        content=bytes;
        JsonArray jsonArray = new JsonArray();

        JsonObject jsonObject= JsonParser.parseString(content).getAsJsonObject();
        jsonArray.add(jsonObject);
        System.out.println("数据量 " +jsonArray.size());

    }

//192.168.1.63:30100
    public static void main(String[] args) throws Exception {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("group_order_consumer");
        //consumer.setNamesrvAddr("120.77.217.160:9876");
        consumer.setNamesrvAddr("192.168.1.63:30100");//30100
        /**
         * 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费<br>
         * 如果非第一次启动，那么按照上次消费的位置继续消费
         */
        //consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        //消费者订阅topic
        consumer.subscribe("userInfo","9990395");
        //消费者注册消息监听器

        JsonArray jsonArray = new JsonArray();
        consumer.registerMessageListener(new MessageListenerOrderly() {
            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {

                for (MessageExt msg : msgs) {

                    // 可以看到每个queue有唯一的consume线程来消费, 订单对每个queue(分区)有序
//                    System.out.println(
//                            "consumeThread=" + Thread.currentThread().getName()
//                                    + ", queueId=" + msg.getQueueId()
//                                    + ", content:" + new String(msg.getBody()));
                    String content=new String(msg.getBody());
                    System.out.println("content:  " + content);
                   // StringToJsonArray(new String(msg.getBody()));



                    JsonObject jsonObject= JsonParser.parseString(content).getAsJsonObject();
                    jsonArray.add(jsonObject);
                    System.out.println("数据量 " +jsonArray.size() + "  ip " +jsonArray.get(0).getAsJsonObject().get("ip"));
                }

                return ConsumeOrderlyStatus.SUCCESS;
            }
        });
        consumer.start();
        System.out.println("consumer starting");
    }


}
