package org.sparta.schedulemanagementapplicationserver.service;

import org.sparta.schedulemanagementapplicationserver.Dto.ScheduleRequestDto;
import org.sparta.schedulemanagementapplicationserver.Dto.ScheduleResponseDto;
import org.sparta.schedulemanagementapplicationserver.entity.Schedule;
import org.sparta.schedulemanagementapplicationserver.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

@Service
public class ScheduleService {
    private ScheduleRepository scheduleRepository;

    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public ScheduleResponseDto createSchedule(ScheduleRequestDto scheduleRequestDto) {
        Schedule schedule = new Schedule(scheduleRequestDto);
        Schedule saveSchedule = scheduleRepository.save(schedule);
        ScheduleResponseDto scheduleResponseDto = new ScheduleResponseDto(saveSchedule);
        return scheduleResponseDto;
    }

    public ScheduleResponseDto getSelectedSchedule(Long id) {
        return new ScheduleResponseDto(findSchedule(id));
    }

    private Schedule findSchedule(Long id) {
        return scheduleRepository.findById(id).orElseThrow(()->
                new IllegalArgumentException("Schedule not found"));
    }
}
