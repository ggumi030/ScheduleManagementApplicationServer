package org.sparta.todoappserver.service;

import org.sparta.todoappserver.dto.user.SignupRequestDto;
import org.sparta.todoappserver.dto.user.SignupResponseDto;
import org.sparta.todoappserver.entity.User;
import org.sparta.todoappserver.entity.UserRoleEnum;
import org.sparta.todoappserver.jwt.JwtUtil;
import org.sparta.todoappserver.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public UserService(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    // ADMIN_TOKEN
    private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";


    public SignupResponseDto signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = requestDto.getPassword(); //패스워드 암호화 하지 않기(security 사용 X)

        // 회원 중복 확인
        Optional<User> checkUsername = userRepository.findByUsername(username);
        if (checkUsername.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        // nickname 중복확인
        String nickname = requestDto.getNickname();
        Optional<User> checkNickname = userRepository.findByNickname(nickname);
        if (checkNickname.isPresent()) {
            throw new IllegalArgumentException("중복된 Nickname 입니다.");
        }

        // 사용자 ROLE 확인
        UserRoleEnum role = UserRoleEnum.USER;
        if (requestDto.isAdmin()) {
            if (!ADMIN_TOKEN.equals(requestDto.getAdminToken())) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            role = UserRoleEnum.ADMIN;
        }

        // 사용자 등록
        User user = new User(username, password, nickname, role);
        userRepository.save(user);

        SignupResponseDto responseDto = new SignupResponseDto("회원가입에 성공했습니다 !", HttpStatus.OK);
        return responseDto;
    }

//    public ResponseEntity<LoginResponseDto> login(LoginRequestDto requestDto, HttpServletResponse httpresponse) {
//        String username = requestDto.getUsername();
//        String password = requestDto.getPassword();
//
//        //사용자 확인
//        User user = userRepository.findByUsername(username).orElseThrow(
//                () -> new IllegalArgumentException("등록된 사용자가 없습니다."));
//
//        //비밀번호 확인
//        if (!password.equals(user.getPassword())) {
//            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
//        }
//
//        //JWT 생성 및 HTTP header에 저장
//        String token = jwtUtil.createToken(user.getUsername(),user.getRole());
//        httpresponse.addHeader(JwtUtil.AUTHORIZATION_HEADER, token);
//
//        LoginResponseDto responseDto = new LoginResponseDto("로그인에 성공했습니다 !", HttpStatus.OK);
//        return new ResponseEntity<>(responseDto,HttpStatus.OK);
//    }
}
