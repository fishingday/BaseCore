package kr.co.basedevice.corebase.dto.todo;

import kr.co.basedevice.corebase.domain.code.Yn;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TodoUserDto {
	private Long userSeq;

	private String loginId;

	private String userNm;

	private String userTelNo;

	private Yn workerAgerYn;
}
