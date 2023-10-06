package kr.co.basedevice.corebase.service.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import kr.co.basedevice.corebase.dto.MailTO;

@Service
public class MailService {
	
    @Autowired
    private JavaMailSender mailSender;

    public void sendMail(MailTO mail) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mail.getAddress());
//        message.setFrom(""); from 값을 설정하지 않으면 application.yml의 username값이 설정됩니다.
        message.setSubject(mail.getTitle());
        message.setText(mail.getMessage());

        mailSender.send(message);
    }
}
