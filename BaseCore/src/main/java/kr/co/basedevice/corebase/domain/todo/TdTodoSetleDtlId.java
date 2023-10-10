package kr.co.basedevice.corebase.domain.todo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TdTodoSetleDtlId implements Serializable {
	
	private static final long serialVersionUID = -8995938541079692049L;

	private Long todoSetleSeq;
	
	private Long workSeq;
}
