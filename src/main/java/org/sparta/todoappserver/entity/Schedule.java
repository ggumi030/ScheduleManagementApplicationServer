package org.sparta.todoappserver.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sparta.todoappserver.Dto.schedule.ScheduleModRequestDto;
import org.sparta.todoappserver.Dto.schedule.ScheduleRequestDto;

import java.util.Objects;

@Entity
@Getter
@Table(name = "schedule")
@NoArgsConstructor
public class Schedule extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="title", nullable=false)
    private String title;
    @Column(name="contents", nullable=true, length=1000)
    private String contents;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;


    public Schedule(ScheduleRequestDto scheduleRequestDto,User user){
        this.title = scheduleRequestDto.getTitle();
        this.contents = scheduleRequestDto.getContents();
        this.user = user;
    }

    public void update(ScheduleModRequestDto scheduleModRequestDto){
        this.title = scheduleModRequestDto.getTitle();
        this.contents = scheduleModRequestDto.getContents();
    }

}
