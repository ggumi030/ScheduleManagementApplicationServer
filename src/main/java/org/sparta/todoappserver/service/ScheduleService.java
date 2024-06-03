package org.sparta.todoappserver.service;

import jakarta.validation.Valid;
import org.sparta.todoappserver.dto.schedule.ScheduleCreateResponseDto;
import org.sparta.todoappserver.dto.schedule.ScheduleModRequestDto;
import org.sparta.todoappserver.dto.schedule.ScheduleRequestDto;
import org.sparta.todoappserver.dto.schedule.ScheduleResponseDto;
import org.sparta.todoappserver.entity.Image;
import org.sparta.todoappserver.entity.Schedule;
import org.sparta.todoappserver.entity.User;
import org.sparta.todoappserver.repository.ImageRepository;
import org.sparta.todoappserver.repository.ScheduleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final ImageRepository imageRepository;
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024; //5MB

    public ScheduleService(ScheduleRepository scheduleRepository, ImageRepository imageRepository) {
        this.scheduleRepository = scheduleRepository;
        this.imageRepository = imageRepository;
    }

    @Transactional
    public ScheduleCreateResponseDto createSchedule(ScheduleRequestDto scheduleRequestDto, User user) throws IOException {
        MultipartFile file = scheduleRequestDto.getImage();

        String originalFilename = file.getOriginalFilename();

        String extension;
        String filename;

        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            filename = originalFilename.substring(0, originalFilename.lastIndexOf("."));

            if(!(extension.equals("png")||extension.equals("jpg"))) {
                throw new IOException("Invalid extension");
            }

        }else{
            throw new IOException("Bad file extension");
        }

        if(file.getSize() > MAX_FILE_SIZE){
            throw new IOException("Invalid file size");
        }

        Image image = new Image(filename,extension,file.getSize(),file.getBytes());

        Schedule schedule = new Schedule(scheduleRequestDto,user,image);
        Schedule saveSchedule = scheduleRepository.save(schedule);
        return new ScheduleCreateResponseDto(saveSchedule,image);
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

    @Transactional
    public Long deleteSchedule(Long id, User user) {
        Schedule schedule = findSchedule(id);
        checkUser(schedule, user);
        scheduleRepository.delete(schedule);
        return id;
    }

    public Schedule findSchedule(Long id) {
        return scheduleRepository.findById(id).orElseThrow(()-> new NoSuchElementException("Schedule not found"));
    }

    public void checkUser(Schedule schedule, User user){
        //사용자 예외
        if(!schedule.getUser().getUsername().equals(user.getUsername())){
            throw new IllegalArgumentException("작성자만 삭제/수정할 수 있습니다.");
        }
    }
}
