package com.FirstGame.server.api;

import com.FirstGame.server.common.BO.User;
import com.FirstGame.server.common.BO.UserLogin;
import com.FirstGame.server.common.BaseResponse;
import com.FirstGame.server.common.ErrorCode;
import com.FirstGame.server.common.Token;
import com.FirstGame.server.repository.JedisClientUtils;
import com.FirstGame.server.repository.UserMapper;
import com.FirstGame.server.service.UserService;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.iohao.game.action.skeleton.annotation.ActionController;
import com.iohao.game.action.skeleton.annotation.ActionMethod;
import com.iohao.game.action.skeleton.core.exception.MsgException;
import com.iohao.game.action.skeleton.core.flow.FlowContext;
import com.iohao.game.bolt.broker.client.kit.ExternalCommunicationKit;
import com.iohao.game.bolt.broker.client.kit.UserIdSettingKit;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



@Slf4j
@Component
@ActionController(UserCmd.cmd)
public class UserAction {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserService userService;

    @Autowired
    private Token token;

    /**
     * 用户注册
     * @param request
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
                    return BaseResponse.fail(500,ErrorCode.DATABASEFAILED.getMsg());
                }
                return BaseResponse.success(200,"注册成功");
            }catch (Exception e){
                log.error("registerUser error ",e);
                return BaseResponse.fail(500,ErrorCode.DATABASEFAILED.getMsg());
            }
        }else{
            return BaseResponse.fail(500,ErrorCode.getMsg(status));
        }
    }

    /**
     * 登录逻辑-快速登陆
     * 存在token,解密出redis的key,redis中数据更新储存时间12h
     * 不存在则正常检查账户密码登录
     * @param userLogin
     * @param flowContext
     * @throws MsgException
     */
    @ActionMethod(UserCmd.loginUser)
    public BaseResponse loginUser(UserLogin userLogin, FlowContext flowContext) throws MsgException {
        log.info("loginUser");
        if( userLogin == null ){
            return BaseResponse.fail(ErrorCode.MISSUSERINFO.getMsg());
        }
        BaseResponse response = userService.selectUserByName(userLogin.getUserName());
        if( !response.isSuccess() ){
            return response;
        }
        User user = JSON.parseObject(response.getData(),User.class);
        Long userId = user.getUserId();
        // 查询用户是否在线
        boolean existUser = ExternalCommunicationKit.existUser(userId);
        if (existUser) {
            log.info("当前玩家 {} 已经在线", userId);
        }
        // 如果账号在线，就抛异常 （断言 + 异常机制）
        ErrorCode.ACCOUNTONLINE.assertTrueThrows(existUser);
        String key = null;
        //账户密码登录
        if( userLogin.getToken() == null && user.getUserName().equals(userLogin.getUserName())
                && user.getPassWord().equals(userLogin.getPassWord())){
            // channel 中设置用户的真实 userId；
            boolean success = UserIdSettingKit.settingUserId(flowContext, user.getUserId());
            key = token.generatedToken(user);
            userService.setUserInRedis(key, user);
        }
        //快速登录
        else if(  userLogin.getToken() != null && JedisClientUtils.exists(userLogin.getToken())){
            // channel 中设置用户的真实 userId；
            boolean success = UserIdSettingKit.settingUserId(flowContext, userId);
            BaseResponse response1 = userService.updateUserInRedis(userLogin.getToken());
            if(response1 == null || !response1.isSuccess() ) {
                return BaseResponse.fail(400,ErrorCode.DATABASEFAILED.getMsg());
            }
            key = response1.getData();
        }
        else{
            return BaseResponse.fail(400,ErrorCode.INCORRECTUSERINFO.getMsg());
        }
        return BaseResponse.success(200,"登录成功",key);
    }

    /**
     * todo 好友搜索，添加好友，好友建数据库
     */

}
