package kr.co.basedevice.corebase.domain.td;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TdCheckerMapId implements Serializable {

	private static final long serialVersionUID = 8059767690213090781L;

	private Long todoSeq;
	
	private Long checkerSeq;
}
