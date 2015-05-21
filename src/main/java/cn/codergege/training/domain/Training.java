package cn.codergege.training.domain;

import java.util.Set;

public class Training {
	private Integer tid;
	private String name;
	private String content;	//培训内容
	private String level;	//培训级别
	private String trainingTime; //培训时间
	private String location;	//培训地点
	private Double creditHour;	//培训学时
	private String trainingLx; //培训类型
	private String trainingOrg;	//培训机构
	private Double credit;
	private Set<Candidate> candidates;
	public Integer getTid() {
		return tid;
	}
	public void setTid(Integer tid) {
		this.tid = tid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getTrainingTime() {
		return trainingTime;
	}
	public void setTrainingTime(String trainingTime) {
		this.trainingTime = trainingTime;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Double getCreditHour() {
		return creditHour;
	}
	public void setCreditHour(Double creditHour) {
		this.creditHour = creditHour;
	}
	public String getTrainingLx() {
		return trainingLx;
	}
	public void setTrainingLx(String trainingLx) {
		this.trainingLx = trainingLx;
	}
	public String getTrainingOrg() {
		return trainingOrg;
	}
	public void setTrainingOrg(String trainingOrg) {
		this.trainingOrg = trainingOrg;
	}
	public Set<Candidate> getCandidates() {
		return candidates;
	}
	public void setCandidates(Set<Candidate> candidates) {
		this.candidates = candidates;
	}
	public Double getCredit() {
		return credit;
	}
	public void setCredit(Double credit) {
		this.credit = credit;
	}
}
