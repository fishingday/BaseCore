package kr.co.basedevice.corebase.domain.cm;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CmRoleMenuMapId implements Serializable {

	private static final long serialVersionUID = 8699491868509867589L;

	private Long roleSeq;
	
	private Long menuSeq;
}
