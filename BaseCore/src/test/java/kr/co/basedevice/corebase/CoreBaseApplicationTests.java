package kr.co.basedevice.corebase;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import kr.co.basedevice.corebase.domain.cm.CmMenu;
import kr.co.basedevice.corebase.domain.cm.CmMenuDtl;
import kr.co.basedevice.corebase.domain.cm.CmMenuDtlRoleMap;
import kr.co.basedevice.corebase.domain.cm.CmRoleMenuMap;
import kr.co.basedevice.corebase.repository.cm.CmMenuRepository;

@SpringBootTest
class CoreBaseApplicationTests {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private CmMenuRepository cmMenuRepository;

    @Test
    void metchPwd() {
    	String pwd = passwordEncoder.encode("1111"); //"$2a$10$ITvL7uD4IV65bzre4IKy7u8tlzmJUmnFdIqpEIT9SYempGwHEWZdq";
    	
    	System.err.println("###########[" + pwd + "]"); 
    	
    	// {bcrypt}$2a$10$1qiyUUZzWWG8PZRAL.BH..qQdWPR1DIOxxjt97ZlsyudyrzMfmK/K
    	// {bcrypt}$2a$10$ErKoq2mRDOVizhm8MDqRnebaDfqRwU47ixtZM58aEIMbdJX3jjxYy
    	
    	System.err.println("###########[" + passwordEncoder.matches("1111", pwd) + "]");	
    	
    }
    
    @Test
    void testEnCypto() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
	    // https://bamdule.tistory.com/234
    	
    	String alg = "AES/CBC/PKCS5Padding";
    	String key = "01234567890123456789012345678901";
    	String iv = key.substring(0, 16); // 16byte
    	
    	String text = "ASC256";
    	
    	 Cipher cipher = Cipher.getInstance(alg);
         SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
         IvParameterSpec ivParamSpec = new IvParameterSpec(iv.getBytes());
         cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParamSpec);

         byte[] encrypted = cipher.doFinal(text.getBytes("UTF-8"));
         String outText = Base64.getEncoder().encodeToString(encrypted);
         
         System.err.println("##################" + outText);
    }
    
    @Test
    void testMenuDtlList() {
    	
    	List<CmMenuDtl> cmMenuDtlList = cmMenuRepository.findAllMenuDtl();
    	
    	for(CmMenuDtl cmMenuDtl : cmMenuDtlList) {
    		System.err.println(cmMenuDtl.getCmMenu().getMenuNm() + "-" + cmMenuDtl.getMenuDtlNm());
    		if(!cmMenuDtl.getCmMenuDtlRoleMapList().isEmpty()) {
	    		for(CmMenuDtlRoleMap cmMenuDtlRoleMap : cmMenuDtl.getCmMenuDtlRoleMapList()) {
	    			System.err.println(cmMenuDtl.getCmMenu().getMenuNm() +"-"+ cmMenuDtl.getMenuDtlNm() 
	    				+ "(" + cmMenuDtl.getCmMenu().getMenuPath() + cmMenuDtl.getMenuDtlPath() + " : " + cmMenuDtl.getDepth() + ")-" 
	    				+ cmMenuDtlRoleMap.getCmRole().getRoleNm() + ":" + cmMenuDtlRoleMap.getCmRole().getRoleNm());
	    		}
    		}
    	}
    }
    
    @Test
    void testMenuList() {
    	
    	List<CmMenu> cmMenuList = cmMenuRepository.findAllMenu();
    	
    	for(CmMenu cmMenu : cmMenuList) {
    		System.err.println(cmMenu.getMenuNm());
    		if(!cmMenu.getCmRoleMenuMapList().isEmpty()) {
	    		for(CmRoleMenuMap cmRoleMenuMap : cmMenu.getCmRoleMenuMapList()) {
	    			System.err.println(cmMenu.getMenuNm() + "(" + cmMenu.getMenuPath() + " : " + cmMenu.getDepth() + ")-" + cmRoleMenuMap.getCmRole().getRoleNm() + ":" + cmRoleMenuMap.getCmRole().getRoleNm());
	    		}
    		}
    	}
    }
}
