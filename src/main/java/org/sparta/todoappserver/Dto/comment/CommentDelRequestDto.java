package org.sparta.todoappserver.Dto.comment;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CommentDelRequestDto {
    @NotNull(message = "댓글 아이디가 필수로 입력되어야합니다.")
    private Long comment_id;

    @NotNull(message = "일정 아이디가 필수로 입력되어야합니다.")
    private Long schedule_id;

    @NotNull(message = "사용자 이름은 필수로 입력되어야합니다.")
    private String username;

}
