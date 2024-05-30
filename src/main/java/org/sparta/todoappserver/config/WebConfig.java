package org.sparta.todoappserver.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.sparta.todoappserver.filter.JwtAuthenticationFilter;
import org.sparta.todoappserver.filter.JwtAuthorizationFilter;
import org.sparta.todoappserver.filter.LoggingFilter;
import org.sparta.todoappserver.service.UserService;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {

    private final LoggingFilter loggingFilter;
    private final JwtAuthorizationFilter jwtAuthorizationFilter;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public WebConfig(
            LoggingFilter loggingFilter,
            JwtAuthorizationFilter jwtAuthorizationFilter,
            JwtAuthenticationFilter jwtAuthenticationFilter
    ){
        this.loggingFilter = loggingFilter;
        this.jwtAuthorizationFilter = jwtAuthorizationFilter;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public FilterRegistrationBean LoggingFilter() {
        FilterRegistrationBean<LoggingFilter> filterRegistrationBean = new
                FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(loggingFilter);
        filterRegistrationBean.setOrder(1);
        filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;
    }


    @Bean
    public FilterRegistrationBean JwtAuthorizationFilter() {
        FilterRegistrationBean<JwtAuthorizationFilter> filterRegistrationBean = new
                FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(jwtAuthorizationFilter);
        filterRegistrationBean.setOrder(2);
        filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;
    }


    @Bean
    public FilterRegistrationBean JwtAuthenticationFilter() {
        FilterRegistrationBean<JwtAuthenticationFilter> filterRegistrationBean = new
                FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(jwtAuthenticationFilter);
        filterRegistrationBean.setOrder(3);
        filterRegistrationBean.addUrlPatterns("/api/user/login");
        return filterRegistrationBean;
    }


}
