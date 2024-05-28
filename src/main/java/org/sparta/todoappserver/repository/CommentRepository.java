package org.sparta.todoappserver.repository;

import org.sparta.todoappserver.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
