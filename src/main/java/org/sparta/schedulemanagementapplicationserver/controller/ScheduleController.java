package org.sparta.schedulemanagementapplicationserver.controller;

import org.sparta.schedulemanagementapplicationserver.Dto.ScheduleRequestDto;
import org.sparta.schedulemanagementapplicationserver.Dto.ScheduleResponseDto;
import org.sparta.schedulemanagementapplicationserver.service.ScheduleService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping("/schedule")
    public ScheduleResponseDto createSchedule(@RequestBody ScheduleRequestDto scheduleRequestDto) {
        return scheduleService.createSchedule(scheduleRequestDto);
    }

    @GetMapping("/schedule/selected/{id}")
    public ScheduleResponseDto getSelectedSchedule(@PathVariable Long id) {
        return scheduleService.getSelectedSchedule(id);
    }

}