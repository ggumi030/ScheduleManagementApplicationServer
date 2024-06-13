package org.sparta.todoappserver.dto.comment;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CommentDelRequestDto {
    @NotNull(message = "댓글 아이디가 필수로 입력되어야합니다.")
    private Long commentId;

    @NotNull(message = "일정 아이디가 필수로 입력되어야합니다.")
    private Long scheduleId;

}
