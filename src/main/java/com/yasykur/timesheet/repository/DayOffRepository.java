package com.yasykur.timesheet.repository;

import com.yasykur.timesheet.model.DayOff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DayOffRepository extends JpaRepository<DayOff, String> {
}
