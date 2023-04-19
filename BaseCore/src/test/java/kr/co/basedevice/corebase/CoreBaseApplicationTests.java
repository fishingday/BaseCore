package kr.co.basedevice.corebase;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class CoreBaseApplicationTests {
	
	@Autowired
	private PasswordEncoder passwordEncoder;

    @Test
    void metchPwd() {
    	String pwd = passwordEncoder.encode("1111"); //"$2a$10$ITvL7uD4IV65bzre4IKy7u8tlzmJUmnFdIqpEIT9SYempGwHEWZdq";
    	
    	System.err.println("###########[" + pwd + "]"); 
    	
    	// {bcrypt}$2a$10$1qiyUUZzWWG8PZRAL.BH..qQdWPR1DIOxxjt97ZlsyudyrzMfmK/K
    	// {bcrypt}$2a$10$ErKoq2mRDOVizhm8MDqRnebaDfqRwU47ixtZM58aEIMbdJX3jjxYy
    	
    	System.err.println("###########[" + passwordEncoder.matches("1111", pwd) + "]");	
    	
    }
}
