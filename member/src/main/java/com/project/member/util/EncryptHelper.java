package com.project.member.util;

public interface EncryptHelper {

    String encrypt(String password);

    boolean isMatch(String password,String hashedPassword);
}
