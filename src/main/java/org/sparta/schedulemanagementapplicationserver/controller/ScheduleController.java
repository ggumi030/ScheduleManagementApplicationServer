package org.sparta.schedulemanagementapplicationserver.controller;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.sparta.schedulemanagementapplicationserver.Dto.ScheduleRequestDto;
import org.sparta.schedulemanagementapplicationserver.Dto.ScheduleModRequestDto;
import org.sparta.schedulemanagementapplicationserver.Dto.ScheduleResponseDto;
import org.sparta.schedulemanagementapplicationserver.service.ScheduleService;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name ="일정 관리 API Controller")
@RestController
@Validated
@RequestMapping("/api")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }


    @Operation(summary="일정 생성")
    @PostMapping("/schedule")
    public ScheduleResponseDto createSchedule(@RequestBody @Valid ScheduleRequestDto scheduleRequestDto) {
        return scheduleService.createSchedule(scheduleRequestDto);
    }


    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "404", description = "선택한 일정이 존재하지 않음")
    })
    @Parameter(name = "id", required = true, schema = @Schema(type = "long", example = "1"))
    @Operation(summary="선택한 일정 조회")
    @GetMapping("/schedule/selected/{id}")
    public ScheduleResponseDto getSelectedSchedule(@NotNull(message = "id must not be null") @PathVariable Long id) {
        return scheduleService.getSelectedSchedule(id);
    }


    @Operation(summary="모든 일정 조회")
    @GetMapping("/schedule")
    public List<ScheduleResponseDto> getAllSchedules() {
        return scheduleService.getAllSchedules();
    }


    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "404", description = "선택한 일정이 존재하지 않음"),
            @ApiResponse(responseCode = "401", description = "비밀번호가 일치하지 않음")
    })
    @Parameter(name = "id", required = true, schema = @Schema(type = "long", example = "1"))
    @Operation(summary="선택한 일정 수정")
    @PutMapping("/schedule/{id}")
    public ScheduleResponseDto updateSchedule( @NotNull(message = "id must not be null") @PathVariable Long id , @RequestBody @Valid ScheduleModRequestDto scheduleModRequestDto) {
        return scheduleService.updateSchedule(id ,scheduleModRequestDto);
    }


    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "404", description = "선택한 일정이 존재하지 않음"),
            @ApiResponse(responseCode = "401", description = "비밀번호가 일치하지 않음")
    })
    @Parameter(name = "id", required = true, schema = @Schema(type = "long", example = "1"))
    @Operation(summary="선택한 일정 삭제")
    @DeleteMapping("/schedule/{id}")
    public Long deleteSchedule(@NotNull(message = "id must not be null") @PathVariable Long id, @NotNull(message = "password must not be null") @RequestParam String checkpassword) {
        return scheduleService.deleteSchedule(id, checkpassword);
    }



}
