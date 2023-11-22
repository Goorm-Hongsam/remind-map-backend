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
        config.addAllowedMethod("*"); // 모든 요청 메소드 응답 허용
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}

//@Configuration
//public class CorsConfig implements WebMvcConfigurer {
//
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**") // 모든 요청에 대해 CORS 설정 적용
//                .allowedOrigins("http://localhost:3000") // 허용할 도메인 지정
//                .allowedMethods("GET", "POST", "PATCH", "DELETE", "HEAD", "OPTIONS") // 허용할 HTTP 메서드 지정
//                .allowedHeaders("*") // 허용할 헤더 지정
//                .allowCredentials(true);
//    }
//}
