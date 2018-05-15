package cn.inphase.domain;

public class Rmpaike {
	private String roomId;
	private String subjectId;
	private String ksId;
	private String teacherId;

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public String getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}

	public String getKsId() {
		return ksId;
	}

	public void setKsId(String ksId) {
		this.ksId = ksId;
	}

	public String getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}

	public Rmpaike(String roomId, String subjectId, String ksId, String teacherId) {
		super();
		this.roomId = roomId;
		this.subjectId = subjectId;
		this.ksId = ksId;
		this.teacherId = teacherId;
	}

	public Rmpaike() {
		super();
	}

}
