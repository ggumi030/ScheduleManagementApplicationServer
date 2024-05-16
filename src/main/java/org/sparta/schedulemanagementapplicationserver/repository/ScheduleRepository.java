package org.sparta.schedulemanagementapplicationserver.repository;

import org.sparta.schedulemanagementapplicationserver.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule,Long> {
    List<Schedule> findAllByOrderByCreatedAtDesc();
}
