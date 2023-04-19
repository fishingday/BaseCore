package kr.co.basedevice.corebase.domain.cm;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CmUserBookmarkId implements Serializable {

	private static final long serialVersionUID = 6757386236673311229L;

	private Long userSeq;
	
	private Long roleSeq;
	
	private Long menuSeq;
}
