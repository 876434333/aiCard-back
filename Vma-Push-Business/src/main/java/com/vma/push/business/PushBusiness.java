package com.vma.push.business;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Spring Boot应用类
 *
 * @author zhangsl
 */
@SpringBootApplication(scanBasePackages = "com.vma.push.business.common.exception")
@ServletComponentScan
@EnableTransactionManagement
@MapperScan("com.vma.push.business.dao")
@ComponentScan("com.vma.push.business")
public class PushBusiness {
    public static void main(String[] args) {
        SpringApplication.run(PushBusiness.class, args);
    }
}
