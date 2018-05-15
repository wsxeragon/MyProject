package cn.inphase.domain;

public class Clapaike {
	private String classId;
	private String subjectId;
	private String ksId;
	private String roomId;

	public Clapaike() {
		super();
	}

	public Clapaike(String classId, String subjectId, String ksId, String roomId) {
		super();
		this.classId = classId;
		this.subjectId = subjectId;
		this.ksId = ksId;
		this.roomId = roomId;
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
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

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

}
