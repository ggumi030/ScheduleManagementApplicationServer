package org.sparta.todoappserver.Dto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import org.sparta.todoappserver.entity.Schedule;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Schema(description = "비밀번호를 제외한 일정 정보 응답 객체")
@Getter
public class ScheduleResponseDto {
    @NotNull(message = "id must not be null")
    @Schema(example = "1", required = true)
    private Long id;

    @NotBlank(message = "title must not be blank")
    @Size(min=1, max= 200, message = "title must be between 1 and 200 characters")
    @Schema(example = "title", required = true)
    private String title;

    @Schema(example = "contents", required = false)
    private String contents;

    @Email(message = "menager 이름은 이메일 형태여야합니다.")
    @Schema(example = "abc@gmail.com", required = true)
    private String manager;

    @NotNull(message = "date must not be null")
    @Schema(example = "2024-05-20 12:33:01.xx", required = true)
    private LocalDateTime createdAt;

    @NotNull(message = "date must not be null")
    @Schema(example = "2024-05-21 12:33:01.xx", required = true)
    private LocalDateTime modifiedAt;

    public ScheduleResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.title = schedule.getTitle();
        this.contents = schedule.getContents();
        this.manager = schedule.getManager();
        this.createdAt = schedule.getCreatedAt();
        this.modifiedAt = schedule.getModifiedAt();
    }
}
