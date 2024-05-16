package org.sparta.schedulemanagementapplicationserver.controller;

import org.sparta.schedulemanagementapplicationserver.Dto.ScheduleRequestDto;
import org.sparta.schedulemanagementapplicationserver.Dto.ScheduleCheckPasswordRequestDto;
import org.sparta.schedulemanagementapplicationserver.Dto.ScheduleResponseDto;
import org.sparta.schedulemanagementapplicationserver.service.ScheduleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/schedule")
    public List<ScheduleResponseDto> getAllSchedules() {
        return scheduleService.getAllSchedules();
    }

//    @PutMapping("/schedule/{id}")
//    public ScheduleResponseDto updateSchedule(@PathVariable Long id, @RequestParam("checkpassword") String password ,@RequestBody ScheduleRequestDto scheduleRequestDto) {
//        return scheduleService.updateSchedule(id ,password, scheduleRequestDto);
//    }

    @PutMapping("/schedule/{id}")
    public ScheduleResponseDto updateSchedule(@PathVariable Long id , @RequestBody ScheduleCheckPasswordRequestDto checkPasswordRequestDto) {
        return scheduleService.updateSchedule(id ,checkPasswordRequestDto);
    }



}
