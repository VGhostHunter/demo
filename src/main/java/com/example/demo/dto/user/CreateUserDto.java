package com.example.demo.dto.user;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author vghosthunter
 */
@Data
public class CreateUserDto {

    @NotBlank(message = "name不能为空")
    private String name;

    @NotBlank(message = "login_name不能为空")
    private String loginName;

    @NotBlank
    private String password;
}
