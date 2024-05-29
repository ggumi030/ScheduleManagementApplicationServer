package org.sparta.todoappserver.Dto.comment;

import lombok.Getter;
import org.sparta.todoappserver.entity.Comment;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {

    private Long id;
    private String contents;
    private String username;
    private Long schedule_id;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    public CommentResponseDto(Comment saveComment) {
        this.id = saveComment.getId();
        this.contents = saveComment.getContents();
        this.username = saveComment.getUser().getUsername();
        this.schedule_id = saveComment.getSchedule().getId();
        this.created_at = saveComment.getCreatedAt();
        this.updated_at = saveComment.getModifiedAt();
    }
}
