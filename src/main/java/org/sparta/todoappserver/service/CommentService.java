package org.sparta.todoappserver.service;

import org.sparta.todoappserver.Dto.comment.CommentDelRequestDto;
import org.sparta.todoappserver.Dto.comment.CommentModRequestDto;
import org.sparta.todoappserver.Dto.comment.CommentRequestDto;
import org.sparta.todoappserver.Dto.comment.CommentResponseDto;
import org.sparta.todoappserver.entity.Comment;
import org.sparta.todoappserver.entity.Schedule;
import org.sparta.todoappserver.entity.User;
import org.sparta.todoappserver.repository.CommentRepository;
import org.sparta.todoappserver.repository.ScheduleRepository;
import org.sparta.todoappserver.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
public class CommentService {

    ScheduleRepository scheduleRepository;
    CommentRepository commentRepository;
    UserRepository userRepository;

    public CommentService(
            ScheduleRepository scheduleRepository,
            CommentRepository commentRepository,
            UserRepository userRepository)
    {
        this.scheduleRepository = scheduleRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
    }

    public CommentResponseDto createComment(CommentRequestDto commentRequestDto) {

        Schedule schedule = scheduleRepository.findById(commentRequestDto.getSchedule_id()).orElseThrow(
                () -> new NoSuchElementException("Schedule not found"));

        User user = userRepository.findByUsername(commentRequestDto.getUsername()).orElseThrow(
                () -> new NoSuchElementException("User not found"));

        Comment comment = new Comment(commentRequestDto,schedule,user);
        Comment saveComment = commentRepository.save(comment);
        return new CommentResponseDto(saveComment);

    }

    @Transactional
    public CommentResponseDto updateComment(CommentModRequestDto commentRequestDto) {
        Comment comment = checkException(
                commentRequestDto.getComment_id(),
                commentRequestDto.getSchedule_id(),
                commentRequestDto.getUsername());

        comment.update(commentRequestDto);

        return new CommentResponseDto(comment);
    }


    public void deleteComment(CommentDelRequestDto commentRequestDto) {
        Comment comment = checkException(
                commentRequestDto.getComment_id(),
                commentRequestDto.getSchedule_id(),
                commentRequestDto.getUsername());

        commentRepository.delete(comment);
    }


    public Comment checkException(Long comment_id, Long schedule_id, String username){
        //댓글 예외
        Comment comment = commentRepository.findById(comment_id).orElseThrow(
                () -> new NoSuchElementException("Comment not found"));

        //일정 예외
        scheduleRepository.findById(schedule_id).orElseThrow(
                () -> new NoSuchElementException("Schedule not found"));

        //사용자 예외
        if(!comment.getUser().getUsername().equals(username)){
            throw new NoSuchElementException("Username이 일치하지 않습니다.");
        }

        return comment;
    }
}
