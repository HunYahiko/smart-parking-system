package com.utm.stanislav.parkingapp.configuration.logger;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoggerConfiguration {
    
    @Bean
    public FilterRegistrationBean<SPSRequestResponseLoggingFilter> requestResponseLoggingFilterRegistrationBean() {
        FilterRegistrationBean<SPSRequestResponseLoggingFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new SPSRequestResponseLoggingFilter());
        return registrationBean;
    }
}
