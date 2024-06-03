package org.sparta.todoappserver.dto.user;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class SignupRequestDto {
    @NotNull(message = "username은 필수로 입력되어야합니다.")
    @Size(min = 4, max = 100, message = "username은 최소 4자 이상, 10자 이하로 구성되어야합니다.")
    @Pattern(regexp = "^[a-zA-Z0-9]+@[0-9a-zA-Z]+\\.[a-z]+$", message = "username은 이메일 형태로 입력되어야합니다.")
    private String username;

    @NotNull(message = "password는 필수로 입력되어야합니다.")
    @Size(min = 8, max = 15, message = "password는 최소 8자 이상, 15자 이하로 구성되어야합니다.")
    @Pattern(regexp = "^[a-zA-z0-9]*$", message = " 알파벳 대소문자(a~z, A~Z), 숫자(0~9)로 입력되어야합니다.")
    private String password;

    @NotNull(message = "nickname은 필수로 입력되어야합니다.")
    private String nickname;

    private boolean admin = false;
    private String adminToken = "";
}
