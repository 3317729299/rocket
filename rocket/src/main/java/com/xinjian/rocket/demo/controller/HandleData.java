package com.xinjian.rocket.demo.controller;

import com.google.gson.Gson;
import com.xinjian.rocket.demo.entity.UserInfo;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@RestController
//http://localhost:8080/click?ip=123&gender=0&age=22&username=tanrundong&location=yunnan&accountId=9990395
public class HandleData {
    @RequestMapping("/click")
    public void click(@RequestParam("ip") String ip,
                      @RequestParam("gender") String gender,
                      @RequestParam("age") String age,
                      @RequestParam("username") String username,
                      @RequestParam("location") String location,
                      @RequestParam("accountId") String accountId) throws MQClientException, RemotingException, InterruptedException, MQBrokerException {
        //写入内存

        List<UserInfo> users = new ArrayList<UserInfo>();
        UserInfo userInfo = new UserInfo(ip, gender, age, username, location, accountId);
        users.add(new UserInfo(ip, gender, age, username, location, accountId));
        //写入文件
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
        producer.start();

        Message message = new Message("userInfo", accountId, "KEY", json.getBytes(StandardCharsets.UTF_8));
        //发送消息
        SendResult result = producer.send(message,100000);
        //发送状态
        System.out.println(result.getSendStatus());


        producer.shutdown();
    }

    // 消费者 接收消息


}
