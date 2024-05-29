package org.sparta.todoappserver.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sparta.todoappserver.Dto.comment.CommentModRequestDto;
import org.sparta.todoappserver.Dto.comment.CommentRequestDto;

@Entity
@Getter
@Table(name ="comment")
@NoArgsConstructor
public class Comment extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "contents", nullable = false)
    private String contents;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name="schedule_id")
    private Schedule schedule;


    public Comment(CommentRequestDto commentRequestDto, Schedule schedule, User user) {
        this.contents = commentRequestDto.getContents();
        this.schedule = schedule;
        this.user = user;
    }

    public void update(CommentModRequestDto commentRequestDto) {
        this.contents = commentRequestDto.getContents();
    }

    //user 추가



}
