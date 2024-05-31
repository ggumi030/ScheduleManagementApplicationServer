package org.sparta.todoappserver.service;

import org.sparta.todoappserver.Dto.schedule.ScheduleModRequestDto;
import org.sparta.todoappserver.Dto.schedule.ScheduleRequestDto;
import org.sparta.todoappserver.Dto.schedule.ScheduleResponseDto;
import org.sparta.todoappserver.entity.Comment;
import org.sparta.todoappserver.entity.Schedule;
import org.sparta.todoappserver.entity.User;
import org.sparta.todoappserver.repository.ScheduleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ScheduleService {
    private ScheduleRepository scheduleRepository;

    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public ResponseEntity<ScheduleResponseDto> createSchedule(ScheduleRequestDto scheduleRequestDto, User user) {
        Schedule schedule = new Schedule(scheduleRequestDto,user);
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

    @Transactional
    public ResponseEntity<ScheduleResponseDto> updateSchedule(Long id, ScheduleModRequestDto scheduleModRequestDto,User user) {
        Schedule schedule = findSchedule(id);
        checkUser(schedule, user);
        schedule.update(scheduleModRequestDto);
        return new ResponseEntity<>(new ScheduleResponseDto(schedule), HttpStatus.OK);
    }

    public Long deleteSchedule(Long id, User user) {
        Schedule schedule = findSchedule(id);
        checkUser(schedule, user);
        scheduleRepository.delete(schedule);
        return id;
    }

    private Schedule findSchedule(Long id) {
        return scheduleRepository.findById(id).orElseThrow(()-> new NoSuchElementException("Schedule not found"));
    }

    public void checkUser(Schedule schedule, User user){
        //사용자 예외
        if(!schedule.getUser().getUsername().equals(user.getUsername())){
            throw new IllegalArgumentException("작성자만 삭제/수정할 수 있습니다.");
        }
    }
}
