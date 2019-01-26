package com.kaven.weixinsell;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class LoggerTest {

    private final Logger logger = LoggerFactory.getLogger(LoggerTest.class);

    @Test
    public void test1(){
        /**
         * 默认日志级别是 info ，在其级别之上的日志级别可以输出相应信息
         * 下面 info、error 级别可以输出相应信息， debug 级别不能输出
         * */
        logger.debug("debug......");
        logger.info("info......");
        logger.error("error......");
    }

    @Test
    public void test2(){

        String name = "Kaven";
        String password = "123456";
        log.debug("debug......");
        log.info("info......");
        log.info("name: {},password: {}",name , password);
        log.error("error......");
    }
}
