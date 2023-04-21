package kr.co.basedevice.corebase;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import kr.co.basedevice.corebase.dto.MailTO;
import kr.co.basedevice.corebase.service.MailService;

@SpringBootTest
public class MailTester {
	
    @Autowired
    private MailService mailService;
	
    @Test
    void contextLoads() {
    }

    @Test
    void sendMail() {
    	// https://bamdule.tistory.com/238
    	
        MailTO mailTO = new MailTO();

        mailTO.setAddress("test@test.co.kr");
        mailTO.setTitle("밤둘레 님이 발송한 이메일입니다.");
        mailTO.setMessage("안녕하세요. 반가워요!");

        mailService.sendMail(mailTO);
    }
}
