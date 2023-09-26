package kr.co.basedevice.corebase.domain.td;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "TD_TODO")
@SequenceGenerator(name = "SEQGEN_TD_TODO", sequenceName = "SEQ_TD_TODO", initialValue = 10000, allocationSize = 1)
public class TdTodo {
	
	private Long todoSeq;
	
	private String todoTitl;
	
	private String todoCont;
	
	private String completCodt;
	
	private Long todoPoint;
	

}
