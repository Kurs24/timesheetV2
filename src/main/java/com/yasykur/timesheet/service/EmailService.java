package com.yasykur.timesheet.service;

public interface EmailService {
    public void sendMail(String subject, String to, String employeeName, String body);
}
