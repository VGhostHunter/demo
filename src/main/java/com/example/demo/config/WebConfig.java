package com.example.demo.config;

import com.example.demo.config.interceptor.AuditInterceptor;
import com.example.demo.config.interceptor.AuthorizeInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author vghosthunter
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private String[] permitUrls = new String[] {"/user/login"};

    @Autowired
    private AuditInterceptor auditInterceptor;

    @Autowired
    private AuthorizeInterceptor authorizeInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(auditInterceptor);
        registry.addInterceptor(authorizeInterceptor)
                .excludePathPatterns(permitUrls);
    }

}
