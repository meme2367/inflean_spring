package com.project.member.util;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class SaltUtil implements EncryptHelper{
    @Override
    public String encrypt(String password) {
        return BCrypt.hashpw(password,BCrypt.gensalt());
    }

    @Override
    public boolean isMatch(String password, String hashedPassword) {
        return BCrypt.checkpw(password,hashedPassword);
    }
}
