package com.yasykur.timesheet.service;

import com.yasykur.timesheet.model.Pin;
import com.yasykur.timesheet.repository.PinRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
