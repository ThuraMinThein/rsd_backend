package com.rsd.yaycha.dto;

import lombok.Data;

@Data
public class CreateUserDTO {

    private int id;
    private String name;
    private String userName;
    private String bio;
    private String password;

}
