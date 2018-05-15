package cn.inphase.domain;

public class Filter {
	// 否向全部用户发送
	private boolean is_to_all;
	// 群发到的标签的tag_id
	private String tag_id;

	public boolean isIs_to_all() {
		return is_to_all;
	}

	public void setIs_to_all(boolean is_to_all) {
		this.is_to_all = is_to_all;
	}

	public String getTag_id() {
		return tag_id;
	}

	public void setTag_id(String tag_id) {
		this.tag_id = tag_id;
	}

}
