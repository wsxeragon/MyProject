package cn.inphase.domain;

/**
 * 自定义异常类(继承运行时异常)
 */
public class InsertException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private String errorCode;
	private String errorMsg

	;

	public InsertException(String errorCode, String errorMsg) {
		super();
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}

	public InsertException(String errorMsg) {
		super();
		this.errorMsg = errorMsg;
	}

	public InsertException() {
		super();
	}

	public String getErrorCode() {
		return errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

}