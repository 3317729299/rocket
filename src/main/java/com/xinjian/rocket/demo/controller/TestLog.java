package com.xinjian.rocket.demo.controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class TestLog {
    private static Logger log = LoggerFactory.getLogger("RocketApplication");
    public static void main(String[] args) {
        log.error("******舞台已搭建，请开始你的表演test******");
        log.error("*创建mastertest*");
    }
}
