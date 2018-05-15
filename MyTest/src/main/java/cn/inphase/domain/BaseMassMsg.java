package cn.inphase.domain;

public class BaseMassMsg {
	private Filter filter;
	protected String msgtype;
	// 是否允许转载 1允许 0不允许
	private String send_ignore_reprint;

	public Filter getFilter() {
		return filter;
	}

	public void setFilter(Filter filter) {
		this.filter = filter;
	}

	public String getSend_ignore_reprint() {
		return send_ignore_reprint;
	}

	public void setSend_ignore_reprint(String send_ignore_reprint) {
		this.send_ignore_reprint = send_ignore_reprint;
	}

}
