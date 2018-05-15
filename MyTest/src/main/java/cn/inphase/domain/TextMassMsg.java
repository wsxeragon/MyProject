package cn.inphase.domain;

import java.util.Map;

public class TextMassMsg extends BaseMassMsg {
	private Map<String, Object> text;

	public TextMassMsg() {
		this.msgtype = "text";
	}

	public Map<String, Object> getText() {
		return text;
	}

	public void setText(Map<String, Object> text) {
		this.text = text;
	}

}
