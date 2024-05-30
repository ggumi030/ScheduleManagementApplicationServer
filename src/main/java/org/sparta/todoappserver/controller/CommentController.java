package org.sparta.todoappserver.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.sparta.todoappserver.Dto.comment.CommentDelRequestDto;
import org.sparta.todoappserver.Dto.comment.CommentModRequestDto;
import org.sparta.todoappserver.Dto.comment.CommentRequestDto;
import org.sparta.todoappserver.Dto.comment.CommentResponseDto;
import org.sparta.todoappserver.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Valid
@RestController
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService CommentService) {
        this.commentService = CommentService;
    }

    @PostMapping("/comment")
    public ResponseEntity<CommentResponseDto> createComment(@Valid @RequestBody CommentRequestDto commentRequestDto,HttpServletRequest request) {
        return commentService.createComment(commentRequestDto,request);
    }

    @PutMapping("/comment")
    public ResponseEntity<CommentResponseDto> updateComment(@Valid @RequestBody CommentModRequestDto commentRequestDto, HttpServletRequest request) {
        return commentService.updateComment(commentRequestDto,request);
    }

    @DeleteMapping("/comment")
    public ResponseEntity<String> deleteComment(@Valid @RequestBody CommentDelRequestDto commentRequestDto, HttpServletRequest request) {
        commentService.deleteComment(commentRequestDto,request);
        return new ResponseEntity<>("댓글을 삭제했습니다 !", HttpStatus.OK);
    }

}
