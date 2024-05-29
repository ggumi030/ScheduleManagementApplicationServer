package org.sparta.todoappserver.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.sparta.todoappserver.Dto.user.LoginRequestDto;
import org.sparta.todoappserver.Dto.user.LoginResponseDto;
import org.sparta.todoappserver.Dto.user.SignupRequestDto;
import org.sparta.todoappserver.Dto.user.SignupResponseDto;
import org.sparta.todoappserver.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Valid
@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("user/signup")
    public ResponseEntity<SignupResponseDto> signup(@Valid @RequestBody SignupRequestDto requestDto) {
        return userService.signup(requestDto);
    }

    @PostMapping("user/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto requestDto, HttpServletResponse httpresponse) {
        return userService.login(requestDto,httpresponse);
    }
}
