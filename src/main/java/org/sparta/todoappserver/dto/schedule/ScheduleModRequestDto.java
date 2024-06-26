package org.sparta.todoappserver.dto.schedule;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Getter;

@Schema(description = "일정 수정 요청 객체")
@Getter
public class ScheduleModRequestDto {

    @NotBlank(message = "title must not be blank")
    @Size(min=1, max= 200,message = "title must be between 1 and 200 characters")
    @Schema(example = "title", required = true)
    private String title;

    @Schema(example = "contents", required = false)
    private String contents;

}
