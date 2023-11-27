package com.backend.remindmap.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOriginPattern("*"); // 모든 ip 에 응답 허용
        config.addAllowedHeader("Authorization"); // 모든 header 응답 허용
        config.addExposedHeader("*");
        config.addAllowedHeader("Content-Type");
        config.addAllowedMethod("*"); // 모든 요청 메소드 응답 허용
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}

