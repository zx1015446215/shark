package com.zx.shark.controller.role;

import com.zx.shark.model.User;
import com.zx.shark.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RequestMapping("/role")
@CrossOrigin(origins = "*")
@Controller
public class RoleManagerController {

    @Autowired
    UserServiceImpl userService;

    @RequestMapping("/manager")
    public ModelAndView manager(){
        ModelAndView modelAndView = new ModelAndView("usermanage");
        List<User> users = userService.findAllUsers();
        modelAndView.addObject("users",users);
        return modelAndView;
    }
}
