package com.zx.shark.mapper;

import com.zx.shark.model.Role;
import com.zx.shark.model.User_Roles;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RoleMapper {

    int insert(Role record);
    int insertUser_Roles(User_Roles user_roles);
    Role selectByUserId(long id);
}