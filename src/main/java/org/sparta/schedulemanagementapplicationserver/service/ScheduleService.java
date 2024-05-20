package org.sparta.schedulemanagementapplicationserver.service;

import org.sparta.schedulemanagementapplicationserver.Dto.ScheduleModRequestDto;
import org.sparta.schedulemanagementapplicationserver.Dto.ScheduleRequestDto;
import org.sparta.schedulemanagementapplicationserver.Dto.ScheduleResponseDto;
import org.sparta.schedulemanagementapplicationserver.entity.Schedule;
import org.sparta.schedulemanagementapplicationserver.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

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

    public ScheduleResponseDto updateSchedule(Long id, ScheduleModRequestDto checkPasswordRequestDto) {
        Schedule schedule = findSchedule(id);

        checkPassword(schedule.getPassword(),checkPasswordRequestDto.getCheckpassword());

        schedule.update(checkPasswordRequestDto);
        return new ScheduleResponseDto(schedule);
    }

    public Long deleteSchedule(Long id, String checkPassword) {
        Schedule schedule = findSchedule(id);

        checkPassword(schedule.getPassword(),checkPassword);

        scheduleRepository.delete(schedule);
        return id;
    }

    private Schedule findSchedule(Long id) {
        return scheduleRepository.findById(id).orElseThrow(()-> new NoSuchElementException("Schedule not found"));
    }

    public void checkPassword(String password, String checkpassword) {
       if(!Objects.equals(password,checkpassword)){
           throw new IllegalArgumentException("Wrong password");
       }
    }
}
