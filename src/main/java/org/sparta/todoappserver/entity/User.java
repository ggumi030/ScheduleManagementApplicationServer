package org.sparta.todoappserver.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "user")
@NoArgsConstructor
public class User extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "nickname", nullable = false,unique = true)
    private String nickname;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING) //Enumerated : Enum 저장할 때 쓰임
    //EnumType.STRING : Enum의 이름 그대로를 데이터베이스에 저장 USER -> USER
    private UserRoleEnum role;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Schedule> scheduleList = new ArrayList<>();


    public User(String username, String password, String nickname, UserRoleEnum role) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.role = role;
    }

    public void addSchedule(Schedule schedule) {
        scheduleList.add(schedule);
        schedule.setUser(this); //외래키 설정
    }
}
