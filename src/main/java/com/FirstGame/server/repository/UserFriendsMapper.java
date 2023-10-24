package com.FirstGame.server.repository;

import com.FirstGame.server.common.BO.UserFriends;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserFriendsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserFriends record);

    int insertSelective(UserFriends record);

    UserFriends selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserFriends record);

    int updateByPrimaryKey(UserFriends record);

    int updateByUserIdAndFriendId(UserFriends record);
}