package kr.co.basedevice.corebase.util;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;

public class AesCryto {

	private SecretKeySpec secretKey;
	private IvParameterSpec IV;
	
	public AesCryto(String reqSecretKey, String iv) throws UnsupportedEncodingException, NoSuchAlgorithmException {
		
		//바이트 배열로부터 SecretKey를 구축
		this.secretKey = new SecretKeySpec(reqSecretKey.getBytes("UTF-8"), "AES");
		//this.IV = new IvParameterSpec(reqSecretKey.substring(0,16).getBytes());
		this.IV = new IvParameterSpec(iv.getBytes());
	}
	
    //AES CBC PKCS5Padding 암호화(Hex | Base64)
	public String AesCBCEncode(String plainText) throws Exception {
		
		//Cipher 객체 인스턴스화(Java에서는 PKCS#5 = PKCS#7이랑 동일)
		Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
		
		//Cipher 객체 초기화
		c.init(Cipher.ENCRYPT_MODE, secretKey, IV);
		
		//Encrpytion/Decryption
		byte[] encrpytionByte = c.doFinal(plainText.getBytes("UTF-8"));
		
		//Hex Encode
		return Hex.encodeHexString(encrpytionByte);
		
		//Base64 Encode
//		return Base64.encodeBase64String(encrpytionByte);
	}

	//AES CBC PKCS5Padding 복호화(Hex | Base64)
	public String AesCBCDecode(String encodeText) throws Exception {

		//Cipher 객체 인스턴스화(Java에서는 PKCS#5 = PKCS#7이랑 동일)
		Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
		
		//Cipher 객체 초기화
		c.init(Cipher.DECRYPT_MODE, secretKey, IV);
		
		//Decode Hex
		byte[] decodeByte = Hex.decodeHex(encodeText.toCharArray());
		
		//Decode Base64
//		byte[] decodeByte = Base64.decodeBase64(encodeText);
		
		//Encrpytion/Decryption
		return new String(c.doFinal(decodeByte), "UTF-8");
	}

	public static void main(String[] args) throws Exception {
		
		/*
		 * 키 값의 바이트 수에 따라서 달라집니다.
		 * AES128 : 키값 16bytes
		 * AES192 : 키값 24bytes
		 * AES256 : 키값 32bytes 
		 */
		
		String plainText = "AesPlainText";
		
		String key_128 = "aeskey1234567898";//AES-128는 128비트(16바이트)의 키
		String key_192 = "aeskey12345678987654321a";//AES-192는 192비트(24바이트)의 키
		String key_256 = "aeskey12345678987654321asekey987";//AES-256는 256비트(32바이트)의 키
		
		String iv = "aesiv12345678912";
		
		AesCryto ase_128_cbc = new AesCryto(key_128, iv);
		String aes128CbcEncode = ase_128_cbc.AesCBCEncode(plainText);
		String aes128CbcDeocde = ase_128_cbc.AesCBCDecode(aes128CbcEncode);
		
		AesCryto ase_192_cbc = new AesCryto(key_192, iv);
		String aes192CbcEncode = ase_192_cbc.AesCBCEncode(plainText);
		String aes192CbcDeocde = ase_192_cbc.AesCBCDecode(aes192CbcEncode);
		
		AesCryto ase_256_cbc = new AesCryto(key_256, iv);
		String aes256CbcEncode = ase_256_cbc.AesCBCEncode(plainText);
		String aes256CbcDeocde = ase_256_cbc.AesCBCDecode(aes256CbcEncode);

		System.out.println("plainText: " + plainText);			
		System.out.println();
		System.out.println("Aes128 Encode CBC: " + aes128CbcEncode);			
		System.out.println("Aes128 Decode CBC: " + aes128CbcDeocde);			
		System.out.println();
		System.out.println("Aes192 Encode CBC: " + aes192CbcEncode);			
		System.out.println("Aes192 Decode CBC: " + aes192CbcDeocde);			
		System.out.println();
		System.out.println("Aes256 Encode CBC: " + aes256CbcEncode);			
		System.out.println("Aes256 Decode CBC: " + aes256CbcDeocde);			

	}
}