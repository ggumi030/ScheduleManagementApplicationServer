package org.sparta.todoappserver.repository;

import org.sparta.todoappserver.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
