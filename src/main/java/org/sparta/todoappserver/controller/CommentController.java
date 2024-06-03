package org.sparta.todoappserver.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.sparta.todoappserver.dto.comment.CommentDelRequestDto;
import org.sparta.todoappserver.dto.comment.CommentModRequestDto;
import org.sparta.todoappserver.dto.comment.CommentRequestDto;
import org.sparta.todoappserver.dto.comment.CommentResponseDto;
import org.sparta.todoappserver.entity.User;
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
        User user = (User) request.getAttribute("user");
        return new ResponseEntity<>(commentService.createComment(commentRequestDto,user), HttpStatus.OK);
    }

    @PutMapping("/comment")
    public ResponseEntity<CommentResponseDto> updateComment(@Valid @RequestBody CommentModRequestDto commentRequestDto, HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        return new ResponseEntity<>(commentService.updateComment(commentRequestDto,user), HttpStatus.OK);
    }

    @DeleteMapping("/comment")
    public ResponseEntity<String> deleteComment(@Valid @RequestBody CommentDelRequestDto commentRequestDto, HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        commentService.deleteComment(commentRequestDto,user);
        return new ResponseEntity<>("댓글을 삭제했습니다 !", HttpStatus.OK);
    }

}
