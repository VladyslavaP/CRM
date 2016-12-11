package ua.nure.crm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import ua.nure.crm.controller.CurrentUserControllerAdvice;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter{

    @Bean
    public HandlerInterceptor getCurrentUserUpdateInterceptor() {
        return new CurrentUserControllerAdvice.CurrentUserUpdateInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getCurrentUserUpdateInterceptor());
    }
}
