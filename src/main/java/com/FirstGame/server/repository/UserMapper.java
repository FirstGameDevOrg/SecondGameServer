package com.FirstGame.server.repository;

import com.FirstGame.server.common.BO.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


import java.util.List;

@Mapper
@Repository
public interface UserMapper {

    List<User> selectAll();
    /**
     * 插入用户
     * @param user
     * @return
     */
    int insertUser(User user);

    /**
     * 通过id或者用户名查询
     * @param id
     * @param userName
     * @return
     */
    User selectByIdOrName(Long userId, String userName);

    /**
     * 更新好友列表
     * @param user
     * @return
     */
    //int addFriend(User user);
}
