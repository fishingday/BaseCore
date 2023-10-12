package kr.co.basedevice.corebase.domain.td;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TdWorkerMapId  implements Serializable {
	
	private static final long serialVersionUID = -7776591826807851074L;

	private Long todoSeq;

	private Long workerSeq;
}
