package cn.inphase.domain;

import java.util.Map;

public class ImageMassMsg extends BaseMassMsg {
	private Map<String, Object> image;

	public ImageMassMsg() {
		this.msgtype = "image";
	}

	public Map<String, Object> getImage() {
		return image;
	}

	public void setImage(Map<String, Object> image) {
		this.image = image;
	}

}
