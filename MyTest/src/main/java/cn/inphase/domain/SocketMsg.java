package cn.inphase.domain;

public class SocketMsg {
	private String toIp;
	private String fromIp;
	private String textBody;

	public String getFromIp() {
		return fromIp;
	}

	public void setFromIp(String fromIp) {
		this.fromIp = fromIp;
	}

	public String getToIp() {
		return toIp;
	}

	public void setToIp(String toIp) {
		this.toIp = toIp;
	}

	public String getTextBody() {
		return textBody;
	}

	public void setTextBody(String textBody) {
		this.textBody = textBody;
	}

	@Override
	public String toString() {
		return "SocketMsg [toIp=" + toIp + ", fromIp=" + fromIp + ", textBody=" + textBody + "]";
	}

}
