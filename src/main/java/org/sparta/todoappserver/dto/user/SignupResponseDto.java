package org.sparta.todoappserver.dto.user;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class SignupResponseDto {
    private String message;
    private String statusCode;

    public SignupResponseDto(String msg, HttpStatus httpStatus) {
        this.message = msg;
        this.statusCode = httpStatus.toString();
    }
}
