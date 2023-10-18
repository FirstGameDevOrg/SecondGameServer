package com.FirstGame.server.api;

import com.FirstGame.server.common.BO.User;
import com.FirstGame.server.common.BaseResponse;
import com.FirstGame.server.common.ErrorCode;
import com.FirstGame.server.repository.UserMapper;
import com.FirstGame.server.service.UserService;
import com.alibaba.fastjson2.JSON;
import com.iohao.game.action.skeleton.annotation.ActionController;
import com.iohao.game.action.skeleton.annotation.ActionMethod;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@Component
@RestController
@ActionController(UserCmd.cmd)
public class UserAction {

    private UserMapper userMapper;
    private UserService userService;

    @Autowired
    public UserAction(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public UserAction(UserMapper userMapper){
        this.userMapper = userMapper;
    }

    /**
     * 用户注册
     * @param request
     * @return
     */
    @ActionMethod(UserCmd.registerUser)
    public BaseResponse registerUser(User request){
        log.info("registerUser HttpServletRequest : {}" , JSON.toJSON(request));
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

}
