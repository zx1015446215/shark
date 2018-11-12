package com.zx.shark.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 首页页面的控制器
 */

@Controller
@CrossOrigin(origins = "*")
@RequestMapping("index")
public class IndexController {


    private static  final Logger logger= LoggerFactory.getLogger(IndexController.class);

    /**
     * 登录页面
     * @return
     */
    @RequestMapping("/login")
    public ModelAndView login(){
        logger.info("login:");
        ModelAndView modelAndView=new ModelAndView("index/login");
        return modelAndView;
    }

    /**
     * 注册页面
     * @param request
     * @return
     */
    @RequestMapping("/register")
    public ModelAndView register(HttpServletRequest request){
        ModelAndView modelAndView=new ModelAndView("index/register");
        return modelAndView;
    }

    @RequestMapping("/main")
    public ModelAndView main(){
        ModelAndView modelAndView=new ModelAndView("index/main");

        return modelAndView;
    }

}
