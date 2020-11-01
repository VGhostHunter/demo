package com.example.demo.config.password;

import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

/**
 * Base64PasswordPasswordEncode
 */
@Component
public class Base64PasswordPasswordEncode implements PasswordEncode {

    @Override
    public String encode(String password) {
        return Base64Utils.encodeToString(password.getBytes());
    }
}
