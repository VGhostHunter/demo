package com.example.demo.dto.user;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author vghosthunter
 */
@Data
public class UserDto {

    private String id;

    @NotBlank
    private String name;

    @NotBlank
    private String loginName;

    public boolean hasPermission(String operate) {
        //TODO
        if(this.loginName.equals("jack")) {
            return true;
        }
        return false;
    }
}
