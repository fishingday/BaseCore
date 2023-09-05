package kr.co.basedevice.corebase.domain.cm;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CmOrgUserMapId implements Serializable {

	private static final long serialVersionUID = 284629226442910572L;

	private Long orgSeq;
	
	private Long userSeq;
}
