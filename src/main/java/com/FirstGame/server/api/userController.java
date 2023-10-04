package com.FirstGame.server.api;

import com.FirstGame.server.common.BO.User;
import com.FirstGame.server.common.BO.UserInRedis;
import com.FirstGame.server.common.ErrorCode;
import com.FirstGame.server.repository.UserMapper;
import com.FirstGame.server.service.UserService;
import com.alibaba.fastjson2.JSON;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Slf4j
@RestController
@RequestMapping(value = "/user")
public class userController {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    /**
     * 用户注册
     * @param request
     * @return
     */
    @RequestMapping(value = "/register", method = POST)
    public String registerUser(@RequestBody User request){
        log.info("registerUser HttpServletRequest : {}" ,JSON.toJSON(request));
        //去除空格
        request.setUserName(request.getUserName().trim());
        int status = userService.checkUser(request);
        log.info("registerUser status : {}",status);
        if( Integer.valueOf(1).equals(status) ){
            try{
                userMapper.insertUser(request);
                return "注册成功";
            }catch (Exception e){
                log.error("registerUser error ",e);
                return ErrorCode.DATABASEFAILED.getMsg();
            }
        }else{
            return ErrorCode.getMsg(status);
        }
    }

    /**
     * 用户登录
     * @param userName
     * @param passWord
     * @return
     */
    @RequestMapping(value = "/login", method = POST)
    public String loginUser(@NonNull String userName,@NonNull  String passWord){
        int status = userService.checkPassword(userName,passWord);
        if( Integer.valueOf(1).equals(status) ){
            return "登录成功";
        }else{
            return ErrorCode.getMsg(status);
        }
    }

    @RequestMapping(value = "/searchUser", method = POST)
    @ResponseBody
    public Object searchUser(Long userId,String userName){
        log.info("searchUser userId : {} userName : {}",userId,userName);
        UserInRedis userInRedis = userService.searchUser(userId,userName);
        return Objects.requireNonNullElse(userInRedis, "用户不存在");
    }

    @RequestMapping(value = "/test", method = POST)
    @ResponseBody
    public void test(){
        for(long i = 1; i < 10; i++){
            User user =userMapper.selectByIdOrName(i,null);
            log.info("user : {}",JSON.toJSON(user));
        }
    }




}
