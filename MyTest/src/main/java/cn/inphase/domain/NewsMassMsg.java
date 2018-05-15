package cn.inphase.domain;

import java.util.Map;

public class NewsMassMsg extends BaseMassMsg {
	private Map<String, Object> mpnews;

	public NewsMassMsg() {
		this.msgtype = "mpnews";
	}

	public Map<String, Object> getMpnews() {
		return mpnews;
	}

	public void setMpnews(Map<String, Object> mpnews) {
		this.mpnews = mpnews;
	}

}
