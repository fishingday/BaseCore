package kr.co.basedevice.corebase.domain.cm;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CmCdDtlId implements Serializable {
	
	private static final long serialVersionUID = 4177649224438128386L;

	private String grpCd;
	
	private String cd;
}
