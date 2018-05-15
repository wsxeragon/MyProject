package cn.inphase.domain;

public class Tchpaike {
	private String roomId;
	private String subjectId;
	private String ksId;
	private String teacherId;
	private String tclassId;
	private String classId;

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

	public String getTclassId() {
		return tclassId;
	}

	public void setTclassId(String tclassId) {
		this.tclassId = tclassId;
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public Tchpaike(String roomId, String subjectId, String ksId, String teacherId, String tclassId, String classId) {
		super();
		this.roomId = roomId;
		this.subjectId = subjectId;
		this.ksId = ksId;
		this.teacherId = teacherId;
		this.tclassId = tclassId;
		this.classId = classId;
	}

	public Tchpaike() {
		super();
	}

}
