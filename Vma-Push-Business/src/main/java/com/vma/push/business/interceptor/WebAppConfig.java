package com.vma.push.business.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by chenzui on 2018/5/7.
 */
@Configuration
public class WebAppConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptorConfig()).addPathPatterns("/V1.0/**").
                excludePathPatterns("/V1.0/common/account/pwd/find").
                excludePathPatterns("/V1.0/common/account/login").excludePathPatterns("/V1.0/common/account/user");
//        registry.addInterceptor(new SysInterceptorConfig()).addPathPatterns("/v1.0/*").excludePathPatterns("/v1.0/system/login");
//        registry.addInterceptor(new SaleInterceptorConfig()).addPathPatterns("/v2.0/**");
        super.addInterceptors(registry);
    }
}
