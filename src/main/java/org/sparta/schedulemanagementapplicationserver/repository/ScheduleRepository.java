package org.sparta.schedulemanagementapplicationserver.repository;

import org.sparta.schedulemanagementapplicationserver.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule,Long> {
}
