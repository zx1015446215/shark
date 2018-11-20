package com.zx.shark.service.impl;

import com.zx.shark.mapper.RoleMapper;
import com.zx.shark.mapper.UserMapper;
import com.zx.shark.model.Role;
import com.zx.shark.model.User;
import com.zx.shark.model.User_Roles;
import com.zx.shark.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {
    private static Logger logger= LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    UserMapper userMapper;

    @Autowired
    RoleMapper roleMapper;

    @Autowired
    RedisTemplate redisTemplate;

    @Transactional
    @Override
    public void registerUser(User user) {
//        存入redis
        ValueOperations<String,User> operations=redisTemplate.opsForValue();
        try {
            userMapper.insert(user);
        }catch (Exception e){
            System.out.println("已存在用户:"+user.getId());
            return ;
        }
        operations.set(user.getUsername(),user,30, TimeUnit.SECONDS);
        logger.info("用户插入缓存 >> " +"id: "+ user.getId()+", username: "+user.getUsername()+",password: "+user.getPassword());
        return ;
        //mongodb插入数据
//       userRepository.save(user);

    }

    @Override
    public User findUserByUsername(String username) {
        ValueOperations<String,User> operations=redisTemplate.opsForValue();
        boolean haskey= redisTemplate.hasKey(username);
        //若缓存中存在
        if(haskey){
            User user=operations.get(username);
            logger.info("从缓存中获取了用户: "+"id: "+ user.getId()+", username: "+user.getUsername()+",password: "+user.getPassword());
            return user;
        }
        User user=userMapper.selectByUsername(username);
        //加入缓存
        if(user!=null){
            operations.set(username,user,1000, TimeUnit.SECONDS); //第三个参数设置过期时间
            logger.info("用户插入缓存 >> " +"id: "+ user.getId()+", username: "+user.getUsername()+",password: "+user.getPassword());
        }
        return user;
    }

    @Override
    public Role findRoleById(long id) {
        return roleMapper.selectByUserId(id);
    }

    @Override
    public List<User> findAllUsers() {
        List<User> users = userMapper.selectAll();
        return users;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        userMapper.deleteById(id);
    }
}
