package org.sparta.todoappserver.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.sparta.todoappserver.dto.schedule.ScheduleModRequestDto;
import org.sparta.todoappserver.dto.schedule.ScheduleRequestDto;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @OneToMany(mappedBy = "schedule", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Comment> commentList = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="image_id")
    private Image image;


    public Schedule(ScheduleRequestDto scheduleRequestDto,User user,Image image){
        this.title = scheduleRequestDto.getTitle();
        this.contents = scheduleRequestDto.getContents();
        this.user = user;
        this.image = image;
    }

    public void update(ScheduleModRequestDto scheduleModRequestDto){
        this.title = scheduleModRequestDto.getTitle();
        this.contents = scheduleModRequestDto.getContents();
    }

    public void addCommentList(Comment comment){
        this.commentList.add(comment);
        comment.setSchedule(this); //외래키 설정
    }

}
