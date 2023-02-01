package com.gaoshan;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author luoqy
 */
@EnableAspectJAutoProxy
@EnableScheduling
@MapperScan({"com.gaoshan.modules.wechat.**.mapper",
        "com.gaoshan.modules.test",
        "com.gaoshan.modules.wechat.**.mapping",
        "com.gaoshan.aspect.*.dao"})
@ComponentScan({"com.ruoyi.common.config","com.gaoshan"})
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class,SecurityAutoConfiguration.class})
public class GaoShanApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(GaoShanApiApplication.class, args);
        System.err.println("GaoShanApiApplication 启动成功！");
    }

}
