package kr.co.basedevice.corebase.domain.td;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TdCheckerWorkerMapId implements Serializable {

	private static final long serialVersionUID = -6607713399298379610L;

	private Long checkerSeq;
	
	private Long workerSeq;
}
