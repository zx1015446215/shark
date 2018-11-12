package com.zx.shark.model;

import jdk.nashorn.internal.ir.annotations.Ignore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
public class User implements UserDetails,Serializable {
    @Id
    @GeneratedValue
    private long id;

    @Column(unique = true)
    private String username;

    private String password;
    private String email;
    private String phoneNo;
    private Timestamp createTime;
    private long createNo;
    private Timestamp updateTime;
    private long updateNo;
    @Transient    //忽略此属性，不注入到数据库的表中
    private Integer type;  //0:超级管理员 1:管理员 2:普通用户


    public User() {
    }

    public User(long id, String username, String password, String email, String phoneNo, Timestamp createTime, long createNo, Timestamp updateTime, long updateNo) {
        this.id=id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNo = phoneNo;
        this.createTime = createTime;
        this.createNo = createNo;
        this.updateTime = updateTime;
        this.updateNo = updateNo;
    }


    @ManyToMany(cascade = CascadeType.REFRESH,fetch = FetchType.LAZY)
    private List<Role> roles=new ArrayList<Role>();

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setCreateNo(long createNo) {
        this.createNo = createNo;
    }

    public void setUpdateNo(long updateNo) {
        this.updateNo = updateNo;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Long getCreateNo() {
        return createNo;
    }

    public void setCreateNo(Long createNo) {
        this.createNo = createNo;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public Long getUpdateNo() {
        return updateNo;
    }

    public void setUpdateNo(Long updateNo) {
        this.updateNo = updateNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
        List<Role> roles=this.getRoles();
        for (Role role:roles){
            auths.add(new SimpleGrantedAuthority(role.getName()));
        }
        return auths;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}