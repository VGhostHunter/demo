package com.example.demo.entity;

import com.example.demo.dto.user.UserDto;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;

/**
 * @author vghosthunter
 */
@Data
@Table
@Entity
@DynamicUpdate()
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class User extends BaseAuditEntity {

    @Id
    @GeneratedValue(generator = "jpa-uuid")
    private String id;

    private String name;

    private String loginName;

    private String password;

    private boolean state = true;


    public UserDto toDto() {
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(this, userDto);
        return userDto;
    }
}
