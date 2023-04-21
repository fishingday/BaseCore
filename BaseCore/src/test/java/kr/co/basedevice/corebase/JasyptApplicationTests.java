package kr.co.basedevice.corebase;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JasyptApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void jasypt() {
        String url = "my_db_url";
        String username = "my_db_username";
        String password = "my_db_password";

        System.err.println(jasyptEncoding(url));
        System.err.println(jasyptEncoding(username));
        System.err.println(jasyptEncoding(password));
        
        // ENC(cojTyxZaMXz4odh/nU70sWH9ZgvMLZzK)
        // ENC(45mGl5qf7TdU7sOdl4k8vAZ3KCvCBK9u)
        // ENC(wAjGnG0meo4/H5S40p2reElTTUabwQLY)
    }

    public String jasyptEncoding(String value) {

        String key = "my_jasypt_key";
        StandardPBEStringEncryptor pbeEnc = new StandardPBEStringEncryptor();
        pbeEnc.setAlgorithm("PBEWithMD5AndDES");
        pbeEnc.setPassword(key);
        return pbeEnc.encrypt(value);
    }

}