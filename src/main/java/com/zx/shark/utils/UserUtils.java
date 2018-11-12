package com.zx.shark.utils;

import com.zx.shark.model.User;
import com.zx.shark.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


@Configuration
public class UserUtils {
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    UserService userService;
    public Long GetUserMessage() {
        Long user_id;
        //从SecurityContextHolder中获取用户名
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = String.valueOf(authentication.getPrincipal());
        //从redis缓存中查找用户信息
        ValueOperations<String,User> operations=redisTemplate.opsForValue();
        boolean haskey= redisTemplate.hasKey(username);
        //若缓存中存在
        if(haskey){
            User user=operations.get(username);
            user_id = user.getId();
        }else if(userService.findUserByUsername(username) != null){
            //根据用户名从数据库中获取用户的id
            User user = userService.findUserByUsername(username);
            user_id = user.getId();
        }else{
            user_id=8888L;
        }
        return user_id;
    }
}
