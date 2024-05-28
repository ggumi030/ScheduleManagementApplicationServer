package org.sparta.todoappserver.service;

import org.sparta.todoappserver.Dto.CommentModRequestDto;
import org.sparta.todoappserver.Dto.CommentRequestDto;
import org.sparta.todoappserver.Dto.CommentResponseDto;
import org.sparta.todoappserver.entity.Comment;
import org.sparta.todoappserver.entity.Schedule;
import org.sparta.todoappserver.repository.CommentRepository;
import org.sparta.todoappserver.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class CommentService {

    ScheduleRepository scheduleRepository;
    CommentRepository commentRepository;

    public CommentService(ScheduleRepository scheduleRepository,CommentRepository commentRepository) {
        this.scheduleRepository = scheduleRepository;
        this.commentRepository = commentRepository;
    }

    public CommentResponseDto createComment(CommentRequestDto commentRequestDto) {

        Schedule schedule = scheduleRepository.findById(commentRequestDto.getSchedule_id()).orElseThrow(
                () -> new NoSuchElementException("Schedule not found"));

        Comment comment = new Comment(commentRequestDto,schedule);
        Comment saveComment = commentRepository.save(comment);
        return new CommentResponseDto(saveComment);

    }

//    public CommentResponseDto updateComment(CommentModRequestDto commentRequestDto) {
//
//    }
}
