package com.xinjian.rocket.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RocketApplication {
    private static Logger log = LoggerFactory.getLogger("RocketApplication");

//    public static void main(String[] args) {
//        SpringApplication.run(RocketApplication.class, args);
//    }
    public static void main(String[] args) {

        SpringApplication.run(RocketApplication.class, args);
       log.info("******舞台已搭建，请开始你的表演******");
    }



}
