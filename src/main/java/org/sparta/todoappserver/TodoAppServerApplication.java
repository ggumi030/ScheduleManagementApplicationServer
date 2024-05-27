package org.sparta.todoappserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class TodoAppServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TodoAppServerApplication.class, args);
    }

}
