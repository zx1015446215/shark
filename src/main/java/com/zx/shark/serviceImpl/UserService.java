package com.zx.shark.serviceImpl;

import com.zx.shark.model.Role;
import com.zx.shark.model.User;
import com.zx.shark.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class UserService implements UserDetailsService {
    @Autowired
    UserServiceImpl userServiceImpl;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userServiceImpl.findUserByUsername(username);
        System.out.println("UserDetails:"+user.getUsername());
        if(user==null){
            throw new UsernameNotFoundException("username is wrong");
        }
        List<Role> auths = new ArrayList<Role>();
        Role roleById = userServiceImpl.findRoleById(user.getId());
        auths.add(roleById);
        user.setRoles(auths);
        return user;
    }

}
