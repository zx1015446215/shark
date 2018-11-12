package com.zx.shark.model;

public class User_Roles {
    private Long user_id;
    private Long roles_id;

    public User_Roles(Long user_id, Long role_id) {
        this.user_id = user_id;
        this.roles_id = role_id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Long getRoles_id() {
        return roles_id;
    }

    public void setRoles_id(Long roles_id) {
        this.roles_id = roles_id;
    }
}
