package kr.co.basedevice.corebase.domain.cm;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CmUserRelatId  implements Serializable {

	private static final long serialVersionUID = -695615900936604810L;

	private Long relatorSeq;

	private Long targeterSeq;
}
