package com.zx.shark.serviceImpl;

import com.zx.shark.model.Role;
import com.zx.shark.model.User;
import com.zx.shark.service.impl.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.DigestUtils;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class AuthenticationProvider implements org.springframework.security.authentication.AuthenticationProvider{
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationProvider.class);
    @Autowired
    UserService userService;
    @Autowired
    UserServiceImpl userServiceImpl;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username=authentication.getName();
        String password= (String) authentication.getCredentials();
        User user =null;
        try {
            user = (User) userService.loadUserByUsername(username);
        }catch (Exception e){
            throw  new UsernameNotFoundException("username is wrong");
        }

        System.out.println(user.getPassword());
        if(DigestUtils.md5DigestAsHex(password.getBytes()).equals(user.getPassword())){
            List<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
            Role role=userServiceImpl.findRoleById(user.getId());
            logger.info("role*********:"+role.getName()+"password:"+password);
            auths.add(new SimpleGrantedAuthority(role.getName()));
            return new UsernamePasswordAuthenticationToken(username,password,auths);
        }else{
            throw new BadCredentialsException("password is wrong");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
