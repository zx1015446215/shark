package com.zx.shark.mapper;

import com.zx.shark.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface UserMapper {
    int deleteByPrimaryKey(String id);

    int insert(User record);

    User selectByPrimaryKey(long id);

    List<User> selectAll();

    int updateByPrimaryKey(User record);

    User selectByUsername(String username);
}