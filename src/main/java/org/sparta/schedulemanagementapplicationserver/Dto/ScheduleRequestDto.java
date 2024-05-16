package org.sparta.schedulemanagementapplicationserver.Dto;

import lombok.Getter;

@Getter
public class ScheduleRequestDto {
    private String title;
    private String contents;
    private String manager;
    private String password;
}
