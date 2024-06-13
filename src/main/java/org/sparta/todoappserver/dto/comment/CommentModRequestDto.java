package org.sparta.todoappserver.dto.comment;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CommentModRequestDto {
    @NotNull(message = "댓글 아이디가 필수로 입력되어야합니다.")
    private Long commentId;

    @NotNull(message = "일정 아이디가 필수로 입력되어야합니다.")
    private Long scheduleId;

    @NotEmpty(message = "댓글 내용은 한 글자 이상 필수로 입력되어야합니다.")
    private String contents;

}
