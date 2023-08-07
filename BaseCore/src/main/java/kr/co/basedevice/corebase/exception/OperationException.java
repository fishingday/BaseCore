package kr.co.basedevice.corebase.exception;

/**
 * 운영 예외
 * 
 * @author fishingday
 *
 */
public class OperationException  extends RuntimeException{

	private static final long serialVersionUID = 1934537366689918378L;

	public OperationException(String msg) {
		super(msg);
	}
}
