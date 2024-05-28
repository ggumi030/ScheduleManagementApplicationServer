package org.sparta.todoappserver.controller;

import jakarta.validation.Valid;
import org.sparta.todoappserver.Dto.CommentModRequestDto;
import org.sparta.todoappserver.Dto.CommentRequestDto;
import org.sparta.todoappserver.Dto.CommentResponseDto;
import org.sparta.todoappserver.service.CommentService;
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
    public CommentResponseDto createComment(@Valid @RequestBody CommentRequestDto commentRequestDto) {
        return commentService.createComment(commentRequestDto);
    }

//    @PutMapping("/comment")
//    public CommentResponseDto updateComment(@Valid @RequestBody CommentModRequestDto commentRequestDto) {
//        return commentService.updateComment(commentRequestDto);
//    }
}
