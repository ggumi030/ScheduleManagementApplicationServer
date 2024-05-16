package org.sparta.schedulemanagementapplicationserver.Dto;

import lombok.Getter;

@Getter
public class ScheduleCheckPasswordRequestDto {
    private String checkpassword;
    private String title;
    private String contents;
    private String manager;
}
