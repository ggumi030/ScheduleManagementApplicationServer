package org.sparta.todoappserver.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.sparta.todoappserver.dto.comment.CommentModRequestDto;
import org.sparta.todoappserver.dto.comment.CommentRequestDto;
import org.sparta.todoappserver.repository.CommentRepository;
import org.sparta.todoappserver.repository.ScheduleRepository;
import org.sparta.todoappserver.service.CommentService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class CommentTest {

    @Test
    @DisplayName("Comment 생성자 테스트")
    public void test1(){
        //given
        Schedule schedule = new Schedule();
        User user = new User();
        CommentRequestDto requestDto = Mockito.mock(CommentRequestDto.class);
        when(requestDto.getContents()).thenReturn("contents");

        //when
        Comment comment = new Comment(requestDto,schedule,user);

        //then
        assertEquals(requestDto.getContents(), comment.getContents());
        assertEquals(schedule, comment.getSchedule());
        assertEquals(user,comment.getUser());
    }

    @Test
    @DisplayName("Comment update 함수 테스트")
    public void test2(){
        //given
        CommentModRequestDto requestDto = Mockito.mock(CommentModRequestDto.class);
        when(requestDto.getContents()).thenReturn("contents");
        Comment comment = new Comment();

        //when
        comment.update(requestDto);

        //then
        assertEquals(requestDto.getContents(),comment.getContents());
    }
}