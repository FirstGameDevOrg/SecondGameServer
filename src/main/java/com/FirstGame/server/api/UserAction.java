package com.FirstGame.server.api;

import com.FirstGame.server.common.BO.*;
import com.FirstGame.server.common.BaseResponse;
import com.FirstGame.server.common.ErrorCode;
import com.FirstGame.server.common.Token;
import com.FirstGame.server.repository.JedisClientUtils;
import com.FirstGame.server.repository.UserMapper;
import com.FirstGame.server.service.UserService;
import com.alibaba.fastjson2.JSON;
import com.iohao.game.action.skeleton.annotation.ActionController;
import com.iohao.game.action.skeleton.annotation.ActionMethod;
import com.iohao.game.action.skeleton.core.CmdInfo;
import com.iohao.game.action.skeleton.core.commumication.BroadcastContext;
import com.iohao.game.action.skeleton.core.exception.MsgException;
import com.iohao.game.action.skeleton.core.flow.FlowContext;
import com.iohao.game.bolt.broker.client.kit.ExternalCommunicationKit;
import com.iohao.game.bolt.broker.client.kit.UserIdSettingKit;
import com.iohao.game.bolt.broker.core.client.BrokerClientHelper;
import lombok.extern.slf4j.Slf4j;
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
     * 存在token,验证是否一致,redis中数据更新储存时间12h
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
        log.info("loginUser selectUserByName userLogin : {}, response : {}",
                JSON.toJSON(userLogin),JSON.toJSON(response));
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
        String userToken = null;
        //账户密码登录
        if( userLogin.getToken() == null && user.getUserName().equals(userLogin.getUserName())
                && user.getPassWord().equals(userLogin.getPassWord())){
            // channel 中设置用户的真实 userId；
            boolean success = UserIdSettingKit.settingUserId(flowContext, user.getUserId());
            userToken = token.generatedToken(user);
            userService.setUserInRedis(userToken, user);
        }
        //快速登录
        else if(  userLogin.getToken() != null && JedisClientUtils.exists(userId.toString())){
            UserInRedis userInRedis = JSON.parseObject(JedisClientUtils.get(userId.toString()),UserInRedis.class);
            if( userInRedis != null && userInRedis.getToken().equals(userLogin.getToken()) ){
                // channel 中设置用户的真实 userId；
                boolean success = UserIdSettingKit.settingUserId(flowContext, userId);
                BaseResponse response1 = userService.updateUserInRedis(userId);
                if(response1 == null || !response1.isSuccess() ) {
                    return BaseResponse.fail(400,ErrorCode.DATABASEFAILED.getMsg());
                }
                userToken = response1.getData();
            }
        }
        else{
            return BaseResponse.fail(400,ErrorCode.INCORRECTUSERINFO.getMsg());
        }
        return BaseResponse.success(200,"登录成功",userToken);
    }


    /**
     * 搜索好友
     * @param user
     * @return
     */
    @ActionMethod(UserCmd.searchUser)
    public UserInRedis searchUser(SimpleUser user){
        log.info("searchUser userId : {} userName : {}",user.getUserId(),user.getUserName());
        return userService.searchUser(user.getUserId(),user.getUserName());
    }

    /**
     * 添加好友，附带好友信息
     * @param userFriends
     * @return
     */
    @ActionMethod(UserCmd.addUser)
    public BaseResponse addUser(SimpleUserFriend userFriends){
        Long userId = userFriends.getUserId();
        Long friendId = userFriends.getFriendId();
        UserInRedis friend = userService.searchUser(userId,null);
        BaseResponse response  = BaseResponse.success(200,"好友申请",JSON.toJSONString(friend));
        CmdInfo cmdInfo = CmdInfo.getCmdInfo(UserCmd.cmd, UserCmd.broadcastMsg);
        // 广播上下文
        BroadcastContext broadcastContext = BrokerClientHelper.getBroadcastContext();
        //将申请者的信息发给好友
        broadcastContext.broadcast(cmdInfo, response, friendId);

        return BaseResponse.success();
    }

    @ActionMethod(UserCmd.agreeAddUser)
    public BaseResponse agreeAddUser(BaseResponse isAgree,FlowContext flowContext){
        if( !isAgree.isSuccess()){
            return BaseResponse.fail();
        }
        UserInRedis friend = JSON.parseObject(isAgree.getData(),UserInRedis.class);
        UserFriends record = new UserFriends();
        Long userId = flowContext.getUserId();
        record.setUserId(userId);
        record.setFriendId(friend.getUserId());
        record.setStatus((byte)1);
        BaseResponse response1 = userService.insertUserFriend(record);
        record.setUserId(friend.getUserId());
        record.setFriendId(userId);
        BaseResponse response2 = userService.insertUserFriend(record);
        if( response1.isSuccess() && response2.isSuccess() ){
            return BaseResponse.success("添加好友成功");
        }
        return BaseResponse.fail("添加好友失败");
    }
}
