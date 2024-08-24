package com.yasykur.timesheet.service;

import com.yasykur.timesheet.model.Pin;
import com.yasykur.timesheet.repository.PinRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PinServiceImpl implements PinService {

    private final PinRepository pinRepository;

    @Override
    public Pin createPin(Integer employeeId) {
        Pin newPin = new Pin(employeeId, UUID.randomUUID().toString());
        pinRepository.save(newPin);
        return newPin;
    }

    @Override
    public boolean verifyPin(String pin) {
        Pin foundPin = pinRepository.findByPin(pin).orElse(null);

        if (foundPin == null) {
            return false;
        }

        if (foundPin.getExpireDate().isBefore(LocalDateTime.now())) {
            return false;
        }

        pinRepository.delete(foundPin);

        return true;
    }
}
