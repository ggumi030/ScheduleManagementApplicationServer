package org.sparta.schedulemanagementapplicationserver.Dto;

import lombok.Getter;
import org.sparta.schedulemanagementapplicationserver.entity.Schedule;

import java.time.LocalDate;

@Getter
public class ScheduleResponseDto {
    private Long id;
    private String title;
    private String contents;
    private String manager;
    private LocalDate createdAt;

    public ScheduleResponseDto(Schedule schedule) {
        this.id = schedule.getId();
        this.title = schedule.getTitle();
        this.contents = schedule.getContents();
        this.manager = schedule.getManager();
        this.createdAt = schedule.getCreatedAt();
    }
}
