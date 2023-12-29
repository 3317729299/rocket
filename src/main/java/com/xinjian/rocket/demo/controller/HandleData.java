package com.xinjian.rocket.demo.controller;

import com.google.gson.Gson;
import com.xinjian.rocket.demo.entity.UserInfo;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@RestController
//http://localhost:8080/click?ip=123&gender=0&age=22&username=tanrundong&location=yunnan&accountId=9990395
public class HandleData {
    private org.apache.logging.log4j.Logger logself= LogManager.getLogger(HandleData.class.getName());

    @RequestMapping("/click")
    public void click(@RequestParam("ip") String ip,
                      @RequestParam("gender") String gender,
                      @RequestParam("age") String age,
                      @RequestParam("username") String username,
                      @RequestParam("location") String location,
                      @RequestParam("accountId") String accountId) throws MQClientException, RemotingException, InterruptedException, MQBrokerException {
        //写入内存
        //Queue LinkedList存入数据
        List<UserInfo> users = new LinkedList<UserInfo>();
        UserInfo userInfo = new UserInfo(ip, gender, age, username, location, accountId);
        users.add(new UserInfo(ip, gender, age, username, location, accountId));
       // System.out.println(" users.size() " +users.size());
         //新开一个线程 处理数据
        Thread thread = new Thread(() -> {
            // 创建一个Gson对象
            Gson gson = new Gson();
            // 将Java对象转换为JSON字符串
            String json = gson.toJson(userInfo);
            System.out.println(json); // 输出: {"name":"John","age":30}
            // 给mq 发消息
            DefaultMQProducer producer = new DefaultMQProducer("order_group_producer");
            // producer.setNamesrvAddr("120.77.217.160:9876");
            producer.setNamesrvAddr("192.168.1.63:30100");
            producer.setVipChannelEnabled(false);// 生产者和消费者添加这行代买
            //192.168.1.63:30100
            try {
                producer.start();
            } catch (MQClientException e) {
                e.printStackTrace();
            }
            Message message = new Message("userInfo", accountId, "KEY", json.getBytes(StandardCharsets.UTF_8));
            logself.log(Level.getLevel("CUSTOMER"),json);
            //发送消息
            SendResult result = null;
            try {
                result = producer.send(message,100000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //发送状态
            System.out.println(result.getSendStatus());
            producer.shutdown();
            //处理完之后 移除该数据
            users.remove(0);
        });
        //调用线程
        thread.start();
    }

    // 消费者 接收消息


}
