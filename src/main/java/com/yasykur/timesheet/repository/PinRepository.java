package com.yasykur.timesheet.repository;

import com.yasykur.timesheet.model.Pin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PinRepository extends JpaRepository<Pin, Integer> {
    Optional<Pin> findByPin(String pin);
    Integer deleteByPin(String pin);
}
