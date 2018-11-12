package com.zx.shark.controller;

import com.alibaba.fastjson.JSONObject;
import com.zx.shark.model.MenuDO;
import com.zx.shark.model.Tree;
import com.zx.shark.service.impl.MenuServiceImpl;
import com.zx.shark.service.impl.UserServiceImpl;
import com.zx.shark.utils.JSONResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 登录成功之后的页面，需要权限访问
 */

@Controller
@CrossOrigin(origins = "*")
@RequestMapping("yummy")
public class YummyController {
    private static Logger logger =LoggerFactory.getLogger(YummyController.class);
    @Autowired
    MenuServiceImpl menuService;
    /**
     * 个人信息修改
     * @return
     */
    @RequestMapping("/reader")
    public ModelAndView reader(){
        ModelAndView modelAndView=new ModelAndView("reader");
        return modelAndView;
    }

    /**
     * 登录成功页面首页
     * @return
     */
    @RequestMapping("/index_v1")
    public ModelAndView index_v1(HttpServletRequest request){
        logger.info("进入index_v1");
        ModelAndView modelAndView=new ModelAndView("index_v1");
        List<Tree<MenuDO>> menus = menuService.listMenuTree();
        modelAndView.addObject("menus", menus);
        modelAndView.addObject("picUrl", "/img/touxiang.jpg");
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(request.getParameterMap().containsKey("user")){
            modelAndView.addObject("name", request.getParameter("user"));
            modelAndView.addObject("username",request.getParameter("user"));
        }else {
            modelAndView.addObject("name", principal);
            modelAndView.addObject("username", principal);
        }
        return modelAndView;
    }

    @RequestMapping("/main")
    public ModelAndView main(){
        ModelAndView modelAndView=new ModelAndView("main");
        return modelAndView;
    }

}
