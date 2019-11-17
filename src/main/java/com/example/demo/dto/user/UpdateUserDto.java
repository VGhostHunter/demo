package com.example.demo.dto.user;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author vghosthunter
 */
@Data
public class UpdateUserDto {

    @NotBlank
    private String id;

    @NotBlank
    private String name;

    @NotBlank
    private String loginName;

}
