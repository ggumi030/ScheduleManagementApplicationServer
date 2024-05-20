package org.sparta.schedulemanagementapplicationserver.Dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(description = "확인용 비밀번호를 포함한 일정 수정 요청 객체")
@Getter
public class ScheduleModRequestDto {
    @Schema(example = "title", required = true)
    private String title;

    @Schema(example = "contents", required = true)
    private String contents;

    @Schema(example = "name", required = true)
    private String manager;

    @Schema(example = "1234", required = true)
    private String checkpassword;
}
