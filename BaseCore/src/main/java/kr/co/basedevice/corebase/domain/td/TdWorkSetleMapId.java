package kr.co.basedevice.corebase.domain.td;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TdWorkSetleMapId implements Serializable {
	
	private static final long serialVersionUID = -8995938541079692049L;

	private Long setleSeq;
	
	private Long workSeq;
}
