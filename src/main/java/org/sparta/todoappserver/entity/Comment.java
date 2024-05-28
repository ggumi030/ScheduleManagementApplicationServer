package org.sparta.todoappserver.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sparta.todoappserver.Dto.CommentRequestDto;

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

    @Column(name = "username", nullable = false)
    private String username;

    @ManyToOne
    @JoinColumn(name="schedule_id")
    private Schedule schedule;


    public Comment(CommentRequestDto commentRequestDto, Schedule schedule) {
        this.contents = commentRequestDto.getContents();
        this.username = commentRequestDto.getUsername();
        this.schedule = schedule;
    }

    //user 추가



}
