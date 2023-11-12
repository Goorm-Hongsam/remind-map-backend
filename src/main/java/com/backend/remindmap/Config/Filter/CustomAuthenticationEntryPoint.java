//package com.backend.remindmap.Config.Filter;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.AuthenticationEntryPoint;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@Component
//@Slf4j
//public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
//    @Override
//    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
//
//        log.info("CustomAuthenticationEntryPoint");
//
//        String exception = (String) request.getAttribute("Authorization");
//        String errorCode;
//
//        if (exception == null) {
//            log.info("에러가 발생");
//            errorCode = "에러";
//            setResponse(response, errorCode);
//            return;
//        }
//
//        if(exception.equals("토큰이 만료되었습니다.")) {
//            errorCode = "토큰이 만료되었습니다.";
//            setResponse (response, errorCode);
//        }
//
//        if(exception.equals("유효하지 않은 토큰입니다.")) {
//            errorCode = "유효하지 않은 토큰입니다.";
//            setResponse(response, errorCode);
//        }
//    }
//
//    private void setResponse(HttpServletResponse response, String errorCode) throws IOException {
//        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//        response.setContentType("application/json;charset=UTF-8");
//        response.getWriter().println("Authorization" + " : " + errorCode);
//    }
//}
