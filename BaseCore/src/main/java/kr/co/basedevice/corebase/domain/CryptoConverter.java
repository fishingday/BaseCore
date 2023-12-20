package kr.co.basedevice.corebase.domain;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * 개인정보 암호화 컨버터
 * 
 * @author fishingday
 *
 */
@Converter
public class CryptoConverter implements AttributeConverter<String, String>{

	@Override
	public String convertToDatabaseColumn(String attribute) {
		// encode
		return null;
	}

	@Override
	public String convertToEntityAttribute(String dbData) {
		// decode
		return null;
	}

}
