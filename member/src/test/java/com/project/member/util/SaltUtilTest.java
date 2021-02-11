package com.project.member.util;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class SaltUtilTest {

    @Autowired
    private EncryptHelper encryptHelper;

    @Test
    public void 비밀번호검증() {
        String password = "dayeon";

        String encrypted = encryptHelper.encrypt(password);

        assertTrue(encryptHelper.isMatch(password,encrypted));
    }
}