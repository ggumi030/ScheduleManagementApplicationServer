package org.sparta.todoappserver.service;

import org.sparta.todoappserver.dto.comment.CommentDelRequestDto;
import org.sparta.todoappserver.dto.comment.CommentModRequestDto;
import org.sparta.todoappserver.dto.comment.CommentRequestDto;
import org.sparta.todoappserver.dto.comment.CommentResponseDto;
import org.sparta.todoappserver.entity.Comment;
import org.sparta.todoappserver.entity.Schedule;
import org.sparta.todoappserver.entity.User;
import org.sparta.todoappserver.repository.CommentRepository;
import org.sparta.todoappserver.repository.ScheduleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
public class CommentService {

    private final ScheduleRepository scheduleRepository;
    private final CommentRepository commentRepository;

    public CommentService(
            ScheduleRepository scheduleRepository,
            CommentRepository commentRepository)
    {
        this.scheduleRepository = scheduleRepository;
        this.commentRepository = commentRepository;

    }

    public CommentResponseDto createComment(CommentRequestDto commentRequestDto, User user) {

        Schedule schedule = scheduleRepository.findById(commentRequestDto.getSchedule_id()).orElseThrow(
                () -> new NoSuchElementException("Schedule not found"));

        Comment comment = new Comment(commentRequestDto,schedule,user);
        Comment saveComment = commentRepository.save(comment);
        return new CommentResponseDto(saveComment);

    }

    @Transactional
    public CommentResponseDto updateComment(CommentModRequestDto commentRequestDto, User user) {

        Comment comment = checkException(
                commentRequestDto.getComment_id(),
                commentRequestDto.getSchedule_id(),
                user.getUsername());

        comment.update(commentRequestDto);

        return new CommentResponseDto(comment);
    }


    public void deleteComment(CommentDelRequestDto commentRequestDto, User user) {

        Comment comment = checkException(
                commentRequestDto.getComment_id(),
                commentRequestDto.getSchedule_id(),
                user.getUsername());

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
            throw new IllegalArgumentException("작성자만 삭제/수정할 수 있습니다.");
        }

        return comment;
    }
}
