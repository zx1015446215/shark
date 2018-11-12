package com.zx.shark.serviceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Configuration
public class LoginFailureHandler implements AuthenticationFailureHandler {
    private static final Logger logger= LoggerFactory.getLogger(LoginFailureHandler.class);
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException{
        logger.info("登录失败");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        logger.info("Exception: "+exception.getMessage());
        if(exception.getMessage().equals("username is wrong")){
            out.write("{\"status\":\"500\",\"msg\":\"120\"}");
        }else{
            out.write("{\"status\":\"500\",\"msg\":\"110\"}");
        }
        out.flush();
        out.close();


    }

}
