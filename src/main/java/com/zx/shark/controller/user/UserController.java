package com.zx.shark.controller.user;

import com.zx.shark.model.User;
import com.zx.shark.service.UserService;
import com.zx.shark.utils.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;
    @RequestMapping
    public ModelAndView main(){
        ModelAndView modelAndView = new ModelAndView("personmsg");
        return modelAndView;
    }

    @RequestMapping("/manager")
    public ModelAndView manager(){
        ModelAndView modelAndView = new ModelAndView("usermanage");
        List<User> users = userService.findAllUsers();
        modelAndView.addObject("users",users);
        return modelAndView;
    }

    @RequestMapping("/delete")
    @ResponseBody
    public JSONResult delete(@RequestParam(value = "id",required = false)Long id){
        System.out.println("进入delete"+id);
        try {
            userService.deleteById(Long.valueOf(id));
        }catch (Exception e){
            System.out.println(e);
            return JSONResult.errorMsg("删除失败");
        }
        return JSONResult.ok();
    }
}
