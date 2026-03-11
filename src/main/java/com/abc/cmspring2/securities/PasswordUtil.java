package com.abc.cmspring2.securities;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtil {

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    
    public static String hash(String rawPassword) {
        return encoder.encode(rawPassword);
    }

    public static boolean matches(String rawPassword, String hashed) {
        return encoder.matches(rawPassword, hashed);
    }
    
    public static void main(String[] args) {
    	System.out.println(encoder.encode("password123"));
    }
}

