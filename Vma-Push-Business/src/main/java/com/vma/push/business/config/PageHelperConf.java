package com.vma.push.business.config;

import com.github.pagehelper.PageHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/** 分页pageHelper配置
 * Created by zhangshilin on 2017/8/13.
 */
@Configuration
public class PageHelperConf {
    @Bean
    public PageHelper pageHelper() {
        System.out.println("MyBatisConfiguration.pageHelper()");
        PageHelper pageHelper = new PageHelper();
        Properties p = new Properties();
        p.setProperty("dialect","mysql");
        p.setProperty("offsetAsPageNum", "false");
        p.setProperty("rowBoundsWithCount", "false");
        p.setProperty("pageSizeZero","false");
        p.setProperty("reasonable", "false");
        pageHelper.setProperties(p);
        return pageHelper;
    }
}
