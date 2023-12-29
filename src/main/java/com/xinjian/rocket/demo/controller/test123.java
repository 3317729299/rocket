package com.xinjian.rocket.demo.controller;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.apache.rocketmq.remoting.protocol.body.TopicList;
import org.apache.rocketmq.remoting.protocol.route.TopicRouteData;
import org.apache.rocketmq.tools.admin.DefaultMQAdminExt;

import java.util.Set;

public class test123 {
    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException {
        DefaultMQAdminExt adminExt = new DefaultMQAdminExt();
        adminExt.setNamesrvAddr("192.168.1.63:30100");
        // 启动服务
        adminExt.start();
        TopicList topicList = adminExt.fetchAllTopicList();
        Set<String> topicList1 = topicList.getTopicList();
        for (String s : topicList1) {
            System.out.println(s);
        }

        System.out.println("----------topic list------------");
        // 获取topic: test下的信息
        TopicRouteData res = adminExt.examineTopicRouteInfo("userInfo");
        System.out.println(res);
    }


}
