package org.sparta.schedulemanagementapplicationserver.Dto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.sparta.schedulemanagementapplicationserver.entity.Schedule;

import java.time.LocalDate;

@Schema(description = "비밀번호를 제외한 일정 정보 응답 객체")
@Getter
public class ScheduleResponseDto {
    @Schema(example = "1", required = true)
    private Long id;

    @Schema(example = "title", required = true)
    private String title;

    @Schema(example = "contents", required = true)
    private String contents;

    @Schema(example = "name", required = true)
    private String manager;

    @Schema(example = "2024-05-20", required = true)
    private LocalDate createdAt;

    public ScheduleResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.title = schedule.getTitle();
        this.contents = schedule.getContents();
        this.manager = schedule.getManager();
        this.createdAt = schedule.getCreatedAt();
    }
}
