package com.FirstGame.server.api;


import com.FirstGame.server.common.BO.User;
import com.FirstGame.server.common.BO.UserInRedis;
import com.FirstGame.server.common.BaseResponse;
import com.FirstGame.server.common.ErrorCode;
import com.FirstGame.server.common.Token;
import com.FirstGame.server.repository.UserMapper;
import com.FirstGame.server.service.UserService;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
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

    @Autowired
    private Token token;

    private final String host = "127.0.0.1";

    private final int port = 8080;

    /**
     * 用户注册
     * @param request
     * @return
     */
    @RequestMapping(value = "/register", method = POST)
    public BaseResponse registerUser(@RequestBody User request){
        log.info("registerUser HttpServletRequest : {}" ,JSON.toJSON(request));
        //去除空格
        request.setUserName(request.getUserName().trim());
        int status = userService.checkUser(request);
        log.info("registerUser status : {}",status);
        if( Integer.valueOf(1).equals(status) ){
            try{
                BaseResponse response = userService.insertUser(request);
                if( response != null && !response.isSuccess() ){
                    log.info("registerUser response : {}",JSON.toJSON(response));
                    return new BaseResponse.Builder().code(500).msg(ErrorCode.DATABASEFAILED.getMsg()).build();
                }
                return new BaseResponse.Builder().code(200).msg("注册成功").build();
            }catch (Exception e){
                log.error("registerUser error ",e);
                return new BaseResponse.Builder().code(500).msg(ErrorCode.DATABASEFAILED.getMsg()).build();
            }
        }else{
            return new BaseResponse.Builder().code(500).msg(ErrorCode.getMsg(status)).build();
        }
    }

    /**
     * 用户登录
     * @param userName
     * @param passWord
     * @return
     */
    @RequestMapping(value = "/login", method = POST)
    public BaseResponse<JSONObject> loginUser(@NonNull String userName, @NonNull  String passWord){
        log.info("loginUser");
        BaseResponse baseResponse = userService.checkPassword(userName,passWord);
        log.info("loginUser response : {}",JSON.toJSON(baseResponse));
        if( baseResponse.isSuccess() ){
            JSONObject params = new JSONObject();
            params.put("RemoteHost",host);
            params.put("RemotePort",String.valueOf(port));
            params.put(Token.tokenKey, token.generatedToken(
                    baseResponse.getData() != null ? (User) baseResponse.getData() : null));
            log.info("loginUser params : {}",params);
            return new BaseResponse.Builder<JSONObject>().code(200).msg("登录成功")
                    .data(params).success(true).build();
        }else{
            return new BaseResponse.Builder<JSONObject>().code(500).msg(baseResponse.getMsg())
                    .data(null).success(false).build();
        }
    }

    @RequestMapping(value = "/searchUser", method = POST)
    @ResponseBody
    public Object searchUser(Long userId,String userName){
        log.info("searchUser userId : {} userName : {}",userId,userName);
        UserInRedis userInRedis = userService.searchUser(userId,userName);
        return Objects.requireNonNullElse(userInRedis, "用户不存在");
    }

    @RequestMapping(value = "/addFriend", method = POST)
    public Object addFriend(Long userId){
       if( userId == null ){
           return null;
       }
       return null;
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
