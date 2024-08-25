package com.yasykur.timesheet.service;

import com.yasykur.timesheet.model.Pin;

public interface PinService {
    public Pin createPin(Integer employeeId);
    public boolean verifyPin(String pin);
    public Pin getPinByPin(String pin);
    public Integer deletePin(String pin);
}
