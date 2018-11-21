package com.zx.shark.controller;

import com.zx.shark.model.User;
import com.zx.shark.service.MailService;
import com.zx.shark.service.impl.UserServiceImpl;
import com.zx.shark.utils.JSONResult;
import com.zx.shark.utils.SendEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Controller
@CrossOrigin(origins = "*")
public class ControllerOne {
    @Autowired
    UserServiceImpl userServiceImpl;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    MailService mailService;
    @RequestMapping("")
    public String main(){
        return  "index/index";
    }

    private static  final Logger logger= LoggerFactory.getLogger(ControllerOne.class);
    @RequestMapping("/logout")
    @ResponseBody
    public String logout(HttpServletRequest request, HttpServletResponse response){
        Authentication auth= SecurityContextHolder.getContext().getAuthentication();
        if(auth!=null){
            new SecurityContextLogoutHandler().logout(request,response,auth);
            logger.info("注销---------------");
        }
        return "redirect:/blog";
    }

    @RequestMapping("/register")
    @ResponseBody
    public JSONResult register(@RequestParam String username,@RequestParam String password,
                               @RequestParam String email){
        String pass = DigestUtils.md5DigestAsHex(password.getBytes());
        //获取随机用户id
        Long user_id=Long.valueOf(new SimpleDateFormat("ssSSS").format(new Date()).toString());
        Timestamp createTime = new Timestamp(new Date().getTime());
        Timestamp updateTime = createTime;
        User user=new User(user_id,username,pass,email,null,"/img/touxiang.jpg",createTime,user_id,updateTime,user_id);
        //用户信息和权限信息关联
        try {
            userServiceImpl.registerUser(user);
        }catch (Exception e){
            logger.info("错误信息: "+e);
            return JSONResult.errorMsg("110");
        }
        return JSONResult.ok();
    }


    @RequestMapping(value = "/sendCode",method = RequestMethod.POST)
    @ResponseBody
    public String sendCode(@RequestParam("email")String to) throws UnsupportedEncodingException, MessagingException {
        Random random = new Random();
        String str = getUUID();
        String code = str.substring(0,4);
        mailService.sendSimpleMail(to,"zhxshark博客","感谢来到zx的博客,您的验证码是:"+code);
        System.out.println("发送成功"+code);
        return code;
    }

    public static String getUUID(){
        UUID uuid=UUID.randomUUID();
        String str = uuid.toString();
        String uuidStr=str.replace("-", "");
        return uuidStr;
    }
}
