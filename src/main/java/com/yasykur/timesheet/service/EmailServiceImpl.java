package com.yasykur.timesheet.service;

import com.yasykur.timesheet.util.EmailUtil;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    String from;

    @Override
    @Async
    public void sendMail(String subject, String to, String employeeName, String body) {
        String mailSend = EmailUtil.generateEmailText(employeeName, body);

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

        try {
            helper.setText(mailSend, true);
            helper.addTo(to);
            helper.setSubject(subject);
            helper.setFrom(from);

            mailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
