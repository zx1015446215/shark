package com.zx.shark.service.impl;

import com.zx.shark.mapper.MenuDOMapper;
import com.zx.shark.mapper.RoleMapper;
import com.zx.shark.model.MenuDO;
import com.zx.shark.model.Tree;
import com.zx.shark.model.User;
import com.zx.shark.service.MenuService;
import com.zx.shark.utils.BuildTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    MenuDOMapper menuDOMapper;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    UserServiceImpl userService;
    @Autowired
    RoleMapper roleMapper;

    @Override
    public List<Tree<MenuDO>> listMenuTree() {
        int user_id;
        //从SecurityContextHolder中获取用户名
        Authentication authentication =SecurityContextHolder.getContext().getAuthentication();
        String username = String.valueOf(authentication.getPrincipal());
        //从redis缓存中查找用户信息
        ValueOperations<String,User> operations=redisTemplate.opsForValue();
        boolean haskey= redisTemplate.hasKey(username);
        //若缓存中存在
        if(haskey){
            User user=operations.get(username);
            user_id = Math.toIntExact(user.getId());
        }else {
            //根据用户名从数据库中获取用户的id
            User user = userService.findUserByUsername(username);
            user_id = Math.toIntExact(user.getId());
        }
        Long role_id = roleMapper.selectByUserId(user_id).getId();
        List<Tree<MenuDO>> trees = new ArrayList<Tree<MenuDO>>();

        List<MenuDO> menuDOs = menuDOMapper.selectByRoleId(role_id);
        for (MenuDO sysMenuDO : menuDOs) {
            Tree<MenuDO> tree = new Tree<MenuDO>();
            tree.setId(sysMenuDO.getMenuId().toString());
            tree.setParentId(sysMenuDO.getParentId().toString());
            tree.setText(sysMenuDO.getName());
            Map<String, Object> attributes = new HashMap<>(16);
            attributes.put("url", sysMenuDO.getUrl());
            attributes.put("icon", sysMenuDO.getIcon());
            tree.setAttributes(attributes);
            trees.add(tree);
        }
        // 默认顶级菜单为０，根据数据库实际情况调整
        List<Tree<MenuDO>> list = BuildTree.buildList(trees, "0");
        return list;
    }
}
