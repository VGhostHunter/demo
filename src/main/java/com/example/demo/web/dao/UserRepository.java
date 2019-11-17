package com.example.demo.web.dao;

import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author vghosthunter
 */
public interface UserRepository extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {

    User findByLoginName(String username);
}
