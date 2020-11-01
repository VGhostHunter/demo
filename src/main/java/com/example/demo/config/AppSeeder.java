package com.example.demo.config;

import com.example.demo.config.password.PasswordEncode;
import com.example.demo.entity.User;
import com.example.demo.web.dao.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class AppSeeder implements ApplicationRunner {

    private final UserRepository userRepository;
    private final PasswordEncode passwordEncode;

    private Logger logger = LoggerFactory.getLogger(getClass());
    public static final String ADMIN = "admin";

    public AppSeeder(UserRepository userRepository, PasswordEncode passwordEncode) {
        this.passwordEncode = passwordEncode;
        Assert.notNull(userRepository,"userRepository must not be null");
        Assert.notNull(passwordEncode,"passwordEncode must not be null");
        this.userRepository = userRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if(userRepository.findByLoginName(ADMIN) == null) {
            User user = new User();
            user.setLoginName(ADMIN);
            user.setName("管理员");
            user.setState(true);
            user.setPassword(passwordEncode.encode("111111"));

            userRepository.save(user);
        }
    }
}
