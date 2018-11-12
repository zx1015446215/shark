package com.zx.shark.controller.oauthlogin;


import com.alibaba.fastjson.JSONObject;
import com.zx.shark.model.User;
import com.zx.shark.service.impl.UserServiceImpl;
import com.zx.shark.utils.HttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

@RequestMapping("login")
@Controller
public class LoginController {
    @Autowired
    UserServiceImpl userService;
    @Autowired
    HttpClient httpClient;
    @Value("${github.client.registered-redirect-uri}")
    private String registeredRedirectUri;
    @Value("${github.client.clientId}")
    private String clientId;
    @Value("${github.client.clientSecret}")
    private String clientSecret;
    @Value("${github.client.callback}")
    private String callback;
    @Value("${github.client.grant_type}")
    private String grantType;
    @Value("${github.client.accessTokenUri}") //api url地址
    private String accessTokenUri;


    @RequestMapping("/qq")
    public String qq(){
        return "test";
    }

    @RequestMapping("/github")
    public String github(HttpServletRequest request){
        System.out.println("------github-------");
        Enumeration em = request.getParameterNames();
        while (em.hasMoreElements()) {
            String name = (String) em.nextElement();
            String value = request.getParameter(name);
            System.out.println("name:"+name+"  value:"+value);
        }
        return "test";
    }
    @RequestMapping("/github/callback")
    public String code(HttpServletRequest request){
        String code = request.getParameter("code");
        String accessToken = getAccessToken(code);
        JSONObject userMessage = getUserMessage(accessToken);
        //从返回的信息中获取所需信息
        Long id = (Long) userMessage.get("id");
        String username = (String) userMessage.get("login");
        String email = (String) userMessage.get("email");
        Timestamp createTime = (Timestamp) userMessage.get("created_at");
        Timestamp updateTime = (Timestamp) userMessage.get("updated_at");
        userService.registerUser(new User(id,username,"0000",email,null,createTime,id,updateTime,id));

        SecurityContext securityContext = new SecurityContext() {
            @Override
            public Authentication getAuthentication() {
                List<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
                auths.add(new SimpleGrantedAuthority("ROLE_USER"));
                return new UsernamePasswordAuthenticationToken(userMessage.get("login"),userMessage.get("id"),auths);
            }

            @Override
            public void setAuthentication(Authentication authentication) {
            }
        };
        SecurityContextHolder.setContext(securityContext);

        return "redirect:/yummy/index_v1?user="+userMessage.get("login");
    }

    private JSONObject getUserMessage(String accessToken) {
        //api url地址
        String url = "https://api.github.com/user?access_token="+accessToken;
        //get请求
        HttpMethod method = HttpMethod.GET;
        // 封装参数，千万不要替换为Map与HashMap，否则参数无法传递
        MultiValueMap<String, String> params= new LinkedMultiValueMap<String, String>();
        System.out.println(accessToken);
//        params.add("access_token", accessToken);
        //发送http请求并返回结果
        String user = httpClient.client(url, method, params);
        System.out.println(user);
        return JSONObject.parseObject(user);
    }

    private String getAccessToken(String code) {

        //get请求
        HttpMethod method = HttpMethod.POST;
        // 封装参数，千万不要替换为Map与HashMap，否则参数无法传递
        MultiValueMap<String, String> params= new LinkedMultiValueMap<String, String>();
        params.add("grant_type", grantType);
        params.add("code",code);
        params.add("redirect_uri",callback);
        params.add("client_id",clientId);
        params.add("client_secret",clientSecret);
        //发送http请求并返回结果
        String client = httpClient.client(accessTokenUri, method, params);
        String[] split = client.split("&");
        String access_token = split[0].substring(split[0].indexOf("=")+1);
        System.out.println(client);
        return access_token;
    }

}
