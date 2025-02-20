package com.rsd.yaycha.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserWithTokenDto {

    private int id;
    private String name;
    private String userName;
    private String accessToken;
}