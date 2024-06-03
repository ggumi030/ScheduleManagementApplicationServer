package org.sparta.todoappserver.controller;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.sparta.todoappserver.dto.schedule.ScheduleModRequestDto;
import org.sparta.todoappserver.dto.schedule.ScheduleRequestDto;
import org.sparta.todoappserver.dto.schedule.ScheduleResponseDto;
import org.sparta.todoappserver.entity.User;
import org.sparta.todoappserver.service.ScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ScheduleResponseDto> createSchedule(
            @RequestBody @Valid ScheduleRequestDto scheduleRequestDto,
            HttpServletRequest request
            )
    {
        User user = (User) request.getAttribute("user");
        return new ResponseEntity<>(scheduleService.createSchedule(scheduleRequestDto,user), HttpStatus.OK);
    }


    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "404", description = "선택한 일정이 존재하지 않음")
    })
    @Parameter(name = "id", required = true, schema = @Schema(type = "long", example = "1"))
    @Operation(summary="선택한 일정 조회")
    @GetMapping("/schedule/selected/{id}")
    public ResponseEntity<ScheduleResponseDto> getSelectedSchedule(@NotNull(message = "id must not be null") @PathVariable Long id) {
        return new ResponseEntity<>(scheduleService.getSelectedSchedule(id), HttpStatus.OK);
    }


    @Operation(summary="모든 일정 조회")
    @GetMapping("/schedule/all")
    public ResponseEntity<List<ScheduleResponseDto>> getAllSchedules() {
        return new ResponseEntity<>(scheduleService.getAllSchedules(), HttpStatus.OK);
    }


    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "404", description = "선택한 일정이 존재하지 않음"),
            @ApiResponse(responseCode = "401", description = "비밀번호가 일치하지 않음")
    })
    @Parameter(name = "id", required = true, schema = @Schema(type = "long", example = "1"))
    @Operation(summary="선택한 일정 수정")
    @PutMapping("/schedule/{id}")
    public ResponseEntity<ScheduleResponseDto> updateSchedule(
            @NotNull(message = "id must not be null") @PathVariable Long id ,
            @RequestBody @Valid ScheduleModRequestDto scheduleModRequestDto,
            HttpServletRequest request) {

        User user = (User) request.getAttribute("user");
        ScheduleResponseDto responseDto= scheduleService.updateSchedule(id ,scheduleModRequestDto,user);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }


    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "404", description = "선택한 일정이 존재하지 않음"),
            @ApiResponse(responseCode = "401", description = "비밀번호가 일치하지 않음")
    })
    @Parameter(name = "id", required = true, schema = @Schema(type = "long", example = "1"))
    @Operation(summary="선택한 일정 삭제")
    @DeleteMapping("/schedule/{id}")
    public Long deleteSchedule(@NotNull(message = "id must not be null") @PathVariable Long id,
                               HttpServletRequest request) {
        User user = (User) request.getAttribute("user");
        return scheduleService.deleteSchedule(id, user);
    }



}
