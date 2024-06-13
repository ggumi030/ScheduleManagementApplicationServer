package org.sparta.todoappserver.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.sparta.todoappserver.dto.comment.CommentModRequestDto;
import org.sparta.todoappserver.dto.comment.CommentRequestDto;

@Entity
@Getter
@Setter
@Table(name ="comment")
@NoArgsConstructor
public class Comment extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "contents", nullable = false)
    private String contents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
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

}
