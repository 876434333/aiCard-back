package com.vma.push.bi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;

/**
 * Spring Boot应用类
 *
 * @author zhangsl
 */
@SpringBootApplication(scanBasePackages = "com.vma.push.bi.common.exception")
@ServletComponentScan
@MapperScan("com.vma.push.bi.dao")
@ComponentScan("com.vma.push.bi")
public class PushBiBusiness {
    public static void main(String[] args) {
        SpringApplication.run(PushBiBusiness.class, args);
    }
}
