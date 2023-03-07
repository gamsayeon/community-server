package com.communityserver.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class sha256Encrypt{

    public static String encrypt(String password){
        StringBuffer hexString = new StringBuffer();
        String enpassword = "";
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            for(int i=0; i < hash.length; i++){
                String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1) hexString.append('0');

                hexString.append(hex);
                enpassword = hexString.toString().toUpperCase();
            }
        }catch (NoSuchAlgorithmException e){
            throw new RuntimeException("암호화 Error",e);
        }

        return enpassword;
    }
}
