package cn.inphase.domain;

public class Stupaike {
	private String stuId, subjectId, ksId, roomId, teacherId;

	public String getStuId() {
		return stuId;
	}

	public void setStuId(String stuId) {
		this.stuId = stuId;
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

	public String getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}

	public Stupaike(String stuId, String subjectId, String ksId, String roomId, String teacherId) {
		super();
		this.stuId = stuId;
		this.subjectId = subjectId;
		this.ksId = ksId;
		this.roomId = roomId;
		this.teacherId = teacherId;
	}

	public Stupaike() {
		super();
	}

}
