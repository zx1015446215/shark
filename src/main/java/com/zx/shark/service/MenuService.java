package com.zx.shark.service;

import com.zx.shark.model.MenuDO;
import com.zx.shark.model.Tree;

import java.util.List;

public interface MenuService {
    List<Tree<MenuDO>> listMenuTree();
}
