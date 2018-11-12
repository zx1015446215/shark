package com.zx.shark.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user")
public class UserController {
    @RequestMapping
    public ModelAndView main(){
        ModelAndView modelAndView = new ModelAndView("personmsg");

        return modelAndView;
    }
}
