package com.FirstGame.server.common;

import com.FirstGame.server.common.BO.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class Token {
    public static final String  tokenKey = "FIRST_GAME_USER_TOKEN";

    @Autowired
    private AES aes;

    public String generatedToken(User user) {
        if (user == null || user.getUserId() <= 0) {
            return "";
        }
        Long userId = user.getUserId();
        Long timestamp = System.currentTimeMillis();
        String tokenValue = aes.encrypt(timestamp+":"+userId);
        return tokenValue;
    }

    public String generatedToken(Long userId) {
        if (userId <= 0) {
            return "";
        }
        Long timestamp = System.currentTimeMillis();
        String tokenValue = aes.encrypt(timestamp+":"+userId);
        return tokenValue;
    }

    public Long getTimestampByToken(String token) {
        if (token == null || token.length() <= 0) {
            return null;
        }
        String[] tokenValue = aes.decrypt(token).split(":");
        if (tokenValue == null || tokenValue.length!= 2) {
            return null;
        }
        String timestamp = tokenValue[0];
        return Long.parseLong(timestamp);
    }

    public Long getUserIdByToken(String token) {
        if (token == null || token.length() <= 0) {
            return null;
        }
        String[] tokenValue = aes.decrypt(token).split(":");
        if (tokenValue == null || tokenValue.length!= 2) {
            return null;
        }
        String userId = tokenValue[1];
        return Long.parseLong(userId);
    }

    public String updateToken(String token){
        Long userId = getUserIdByToken(token);
        return generatedToken(userId);
    }
}
