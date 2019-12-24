package com.fjx.config;/*
 @author Jason
 @DESCRIPTION 解决跨域问题
 @create 2019-12-24
*/

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfiguration {
    @Bean
    public CorsFilter corsFilter() {
        //1.添加CORS配置信息，用来初始化Cors配置对象
        org.springframework.web.cors.CorsConfiguration config = new org.springframework.web.cors.CorsConfiguration();

        //1) 添加允许的跨域域名,如果携带cookie，则不可以写* *，表示所有的域名都可以跨域访问
        config.addAllowedOrigin("http://manage.leyou.com");
        //2) 是否允许携带发送Cookie信息，true表示允许迭代cookie信息
        config.setAllowCredentials(true);
        //3) 允许的请求方式,*代表所有的请求方法，包括GET
        // config.addAllowedMethod("*");
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("HEAD");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("PATCH");
        // 4）允许的头信息
        config.addAllowedHeader("*");

        //2.添加映射路径，我们拦截一切请求，初始化cors配置源对象
        UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();
        configSource.registerCorsConfiguration("/**", config);
        //3.返回新的CorsFilter实例，参数为：cors配置源对象
        return new CorsFilter(configSource);
    }
}
