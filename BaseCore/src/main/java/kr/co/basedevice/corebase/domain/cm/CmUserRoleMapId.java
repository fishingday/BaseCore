package kr.co.basedevice.corebase.domain.cm;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CmUserRoleMapId implements Serializable {

	private static final long serialVersionUID = 2078531745342053954L;

	private Long userSeq;

	private Long roleSeq;
}
