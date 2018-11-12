package com.zx.shark.mapper;

import com.zx.shark.model.MenuDO;
import com.zx.shark.model.Tree;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MenuDOMapper {
    List<MenuDO> selectAll();
    List<MenuDO> selectByRoleId(Long roleId);
}