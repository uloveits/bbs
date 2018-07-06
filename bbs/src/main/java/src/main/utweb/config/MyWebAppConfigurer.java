package src.main.utweb.config;

import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import src.main.utweb.interceptor.MyInterceptor1;

//@Configuration
public class MyWebAppConfigurer extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new MyInterceptor1()).addPathPatterns("/**/*.do");
        super.addInterceptors(registry);
    }

}