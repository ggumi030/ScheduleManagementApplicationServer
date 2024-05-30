package org.sparta.todoappserver.service;

import org.sparta.todoappserver.Dto.schedule.ScheduleModRequestDto;
import org.sparta.todoappserver.Dto.schedule.ScheduleRequestDto;
import org.sparta.todoappserver.Dto.schedule.ScheduleResponseDto;
import org.sparta.todoappserver.entity.Schedule;
import org.sparta.todoappserver.repository.ScheduleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ScheduleService {
    private ScheduleRepository scheduleRepository;

    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public ResponseEntity<ScheduleResponseDto> createSchedule(ScheduleRequestDto scheduleRequestDto) {
        Schedule schedule = new Schedule(scheduleRequestDto);
        Schedule saveSchedule = scheduleRepository.save(schedule);
        ScheduleResponseDto scheduleResponseDto = new ScheduleResponseDto(saveSchedule);
        return new ResponseEntity<>(scheduleResponseDto, HttpStatus.OK);
    }

    public ResponseEntity<ScheduleResponseDto> getSelectedSchedule(Long id) {
        return new ResponseEntity<>(new ScheduleResponseDto(findSchedule(id)), HttpStatus.OK);
    }

    public ResponseEntity<List<ScheduleResponseDto>> getAllSchedules() {
        return new ResponseEntity<>(scheduleRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(ScheduleResponseDto::new)
                .toList(), HttpStatus.OK);
    }

    public ResponseEntity<ScheduleResponseDto> updateSchedule(Long id, ScheduleModRequestDto checkPasswordRequestDto) {
        Schedule schedule = findSchedule(id);

        schedule.checkPassword(checkPasswordRequestDto.getCheckpassword());

        schedule.update(checkPasswordRequestDto);
        return new ResponseEntity<>(new ScheduleResponseDto(schedule), HttpStatus.OK);
    }

    public Long deleteSchedule(Long id, String checkPassword) {
        Schedule schedule = findSchedule(id);

        schedule.checkPassword(checkPassword);

        scheduleRepository.delete(schedule);
        return id;
    }

    private Schedule findSchedule(Long id) {
        return scheduleRepository.findById(id).orElseThrow(()-> new NoSuchElementException("Schedule not found"));
    }
}
