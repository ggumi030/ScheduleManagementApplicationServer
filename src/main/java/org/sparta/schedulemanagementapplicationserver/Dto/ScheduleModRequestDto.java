package org.sparta.schedulemanagementapplicationserver.Dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Getter;

@Schema(description = "확인용 비밀번호를 포함한 일정 수정 요청 객체")
@Getter
public class ScheduleModRequestDto {

    @NotBlank(message = "title must not be blank")
    @Size(min=1, max= 200,message = "title must be between 1 and 200 characters")
    @Schema(example = "title", required = true)
    private String title;

    @Schema(example = "contents", required = false)
    private String contents;

    @Email(message = "이메일 형태여야합니다.")
    @Schema(example = "abc@gmail.com", required = true)
    private String manager;

    @NotNull(message = "password must not be null")
    @Schema(example = "1234", required = true)
    private String checkpassword;
}
