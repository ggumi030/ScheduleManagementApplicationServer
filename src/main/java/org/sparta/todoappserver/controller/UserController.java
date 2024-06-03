package org.sparta.todoappserver.controller;

import jakarta.validation.Valid;
import org.sparta.todoappserver.dto.user.LoginResponseDto;
import org.sparta.todoappserver.dto.user.SignupRequestDto;
import org.sparta.todoappserver.dto.user.SignupResponseDto;
import org.sparta.todoappserver.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        return new ResponseEntity<>(userService.signup(requestDto),HttpStatus.OK);
    }

    @GetMapping("user/login")
    public ResponseEntity<LoginResponseDto> login() {
        LoginResponseDto responseDto = new LoginResponseDto("로그인에 성공했습니다 !", HttpStatus.OK);
        return new ResponseEntity<>(responseDto,HttpStatus.OK);
    }
}
