package org.sparta.todoappserver.service;

import org.sparta.todoappserver.dto.schedule.ScheduleModRequestDto;
import org.sparta.todoappserver.dto.schedule.ScheduleRequestDto;
import org.sparta.todoappserver.dto.schedule.ScheduleResponseDto;
import org.sparta.todoappserver.entity.Schedule;
import org.sparta.todoappserver.entity.User;
import org.sparta.todoappserver.repository.ScheduleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    public ScheduleResponseDto createSchedule(ScheduleRequestDto scheduleRequestDto, User user) {
        Schedule schedule = new Schedule(scheduleRequestDto,user);
        Schedule saveSchedule = scheduleRepository.save(schedule);
        return new ScheduleResponseDto(saveSchedule);
    }

    public ScheduleResponseDto getSelectedSchedule(Long id) {
        return new ScheduleResponseDto(findSchedule(id));
    }

    public List<ScheduleResponseDto> getAllSchedules() {
        return scheduleRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(ScheduleResponseDto::new)
                .toList();
    }

    @Transactional
    public ScheduleResponseDto updateSchedule(Long id, ScheduleModRequestDto scheduleModRequestDto,User user) {
        Schedule schedule = findSchedule(id);
        checkUser(schedule, user);
        schedule.update(scheduleModRequestDto);
        return new ScheduleResponseDto(schedule);
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
