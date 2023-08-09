package kr.co.basedevice.corebase.dto.system;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class SaveUserPwd {
	private List<Long> userSeqList; 
	private String chgPwd;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate acuntExpDt;
}
