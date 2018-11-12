package com.zx.shark.controller.article;

import com.zx.shark.model.ContentDO;
import com.zx.shark.model.User;
import com.zx.shark.service.ContentService;
import com.zx.shark.service.impl.UserServiceImpl;
import com.zx.shark.utils.JSONResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/article")
@CrossOrigin(origins = "*")
@Controller
public class ArticleController {
    private static Logger logger= LoggerFactory.getLogger(ArticleController.class);
    @Autowired
    ContentService contentService;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    UserServiceImpl userService;
    @RequestMapping
    public ModelAndView index(){
        ModelAndView modelAndView = new ModelAndView("articlelist");
        Map<String,Object> map = new HashMap<>();
        Long created;
        //从Security获取用户名
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username= (String) authentication.getPrincipal();
        //获取当前用户的Id,从数据库或者从redis缓存
        ValueOperations<String,User> operations=redisTemplate.opsForValue();
        boolean haskey= redisTemplate.hasKey(username);
        //若缓存中存在
        if(haskey){
            User user=operations.get(username);
            logger.info("从缓存中获取了用户: "+"id: "+ user.getId()+", username: "+user.getUsername()+",password: "+user.getPassword());
            created = user.getId();
        }else {
            //根据用户名从数据库中获取用户的id
            User user = userService.findUserByUsername(username);
            created = user.getId();
        }
        map.put("created",created);
        List<ContentDO> contents = contentService.list(map);
        modelAndView.addObject("contents",contents);
        return modelAndView;
    }

    @RequestMapping("/addArticle")
    @ResponseBody
    public JSONResult addArticle(@RequestParam String title,@RequestParam String author,
                                 @RequestParam String categories,@RequestParam String content){
        Long created=1L;
        //从Security获取用户名
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username= (String) authentication.getPrincipal();
        //获取当前用户的Id,从数据库或者从redis缓存
        ValueOperations<String,User> operations=redisTemplate.opsForValue();
        boolean haskey= redisTemplate.hasKey(username);
        //若缓存中存在
        if(haskey){
            User user=operations.get(username);
            logger.info("从缓存中获取了用户: "+"id: "+ user.getId()+", username: "+user.getUsername()+",password: "+user.getPassword());
            created = user.getId();
        }
        //获取当前时间
        Timestamp date = new Timestamp(System.currentTimeMillis());
        Timestamp gtm_create = date;
        Timestamp gtm_modified =date;
        System.out.println(date);
        ContentDO contentDO = new ContentDO(title,created,created,content,categories,author,gtm_create,gtm_modified);
        try {
            contentService.save(contentDO);
        }catch (Exception e){
            System.out.println("添加博客出错信息:"+e.toString());
            return JSONResult.errorMsg("添加博客文章出错");
        }
        return JSONResult.ok();
    }
    @RequestMapping("/updateArticle")
    @ResponseBody
    public JSONResult updateArticle(@RequestParam Long cid,@RequestParam String title,@RequestParam String author,
                                    @RequestParam String categories,@RequestParam String content){
        Timestamp gtm_modified = new Timestamp(System.currentTimeMillis());
        ContentDO contentDO = new ContentDO(cid,title,content,categories,author,gtm_modified);
        System.out.println(contentDO.toString());
        try {
            contentService.update(contentDO);
        }catch (Exception e){
            System.out.println("修改文章错误信息："+e.toString());
            return  JSONResult.errorMsg("修改文章出错");
        }
        return JSONResult.ok();
    }
}
