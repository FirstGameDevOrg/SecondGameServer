package com.FirstGame.server;

import com.FirstGame.server.common.BO.User;
import com.FirstGame.server.repository.UserMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;


@SpringBootTest
@RunWith(SpringRunner.class)

class ServerApplicationTests {


    private UserMapper userMapper;

	@Test
	void contextLoads() {
		User user = new User();
		user.setUserName("test");
		user.setPassWord("test");
		user.setSex(1);
		user.setEmailAddress("test@example.com");
		user.setPhone("123456");
		userMapper.insertUser(user);
	}

}
