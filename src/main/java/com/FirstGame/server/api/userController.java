package com.FirstGame.server.api;

import com.FirstGame.server.common.BO.User;
import com.FirstGame.server.common.ErrorCode;
import com.FirstGame.server.repository.UserMapper;
import com.FirstGame.server.service.UserService;
import com.alibaba.fastjson2.JSON;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Slf4j
@RestController
@RequestMapping(value = "/user")
public class userController {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/register", method = POST)
    @ResponseBody
    public String registerUser(HttpServletRequest request){
        User user = JSON.parseObject(request.getParameter("user"), User.class);
        int status = userService.checkUser(user);
        if( Integer.valueOf(1).equals(status) ){
            userMapper.insertUser(user);
            return "注册成功";
        }else{
            return ErrorCode.getMsg(status);
        }
    }
}
