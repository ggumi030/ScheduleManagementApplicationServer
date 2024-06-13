package org.sparta.todoappserver.dto.comment;

import lombok.Getter;
import org.sparta.todoappserver.entity.Comment;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {

    private Long id;
    private String contents;
    private String username;
    private Long scheduleId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public CommentResponseDto(Comment saveComment) {
        this.id = saveComment.getId();
        this.contents = saveComment.getContents();
        this.username = saveComment.getUser().getUsername();
        this.scheduleId = saveComment.getSchedule().getId();
        this.createdAt = saveComment.getCreatedAt();
        this.updatedAt = saveComment.getModifiedAt();
    }
}
