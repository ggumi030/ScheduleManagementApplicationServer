package org.sparta.todoappserver.Dto.user;

import lombok.Getter;

@Getter
public class LoginRequestDto {
    private String username;
    private String password;
}
