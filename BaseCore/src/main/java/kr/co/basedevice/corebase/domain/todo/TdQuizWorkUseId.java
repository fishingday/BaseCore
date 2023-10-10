package kr.co.basedevice.corebase.domain.todo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TdQuizWorkUseId implements Serializable {

	private static final long serialVersionUID = 6247422308560833494L;

	private Long quizSeq;
	
	private Long workSeq;
}
