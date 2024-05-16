package org.sparta.schedulemanagementapplicationserver.service;

import org.sparta.schedulemanagementapplicationserver.Dto.ScheduleCheckPasswordRequestDto;
import org.sparta.schedulemanagementapplicationserver.Dto.ScheduleRequestDto;
import org.sparta.schedulemanagementapplicationserver.Dto.ScheduleResponseDto;
import org.sparta.schedulemanagementapplicationserver.entity.Schedule;
import org.sparta.schedulemanagementapplicationserver.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<ScheduleResponseDto> getAllSchedules() {
        return scheduleRepository.findAllByOrderByCreatedAtDesc().stream().map(ScheduleResponseDto::new).toList();
    }

    public ScheduleResponseDto updateSchedule(Long id, ScheduleCheckPasswordRequestDto checkPasswordRequestDto) {
        Schedule schedule = findSchedule(id);
        if(schedule.getPassword().equals(checkPasswordRequestDto.getCheckpassword())){
            schedule.update(checkPasswordRequestDto);
            return new ScheduleResponseDto(schedule);
        }else{
            throw new IllegalArgumentException("Wrong password");
        }
    }

    private Schedule findSchedule(Long id) {
        return scheduleRepository.findById(id).orElseThrow(()->
                new IllegalArgumentException("Schedule not found"));
    }

}
