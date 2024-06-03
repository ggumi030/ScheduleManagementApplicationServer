package org.sparta.todoappserver.dto.schedule;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Schema(description = "일정 생성 요청 객체")
@Getter
@AllArgsConstructor
public class ScheduleCreateRequestDto {

    @NotBlank(message = "title must not be blank")
    @Size(min=1, max= 200, message = "title must be between 1 and 200 characters")
    @Schema(example = "title", required = true)
    private String title;

    @Schema(required = false)
    private String contents;

    private MultipartFile image;

}