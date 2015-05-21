package cn.codergege.training.domain;

import java.util.Date;

public class User {
	private Integer id;
	private String name;
	private String loginName;
	private String password;
	private Date regTime;
	private Date lastLogin;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getRegTime() {
		return regTime;
	}
	public void setRegTime(Date regTime) {
		this.regTime = regTime;
	}
	public Date getLastLogin() {
		return lastLogin;
	}
	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", loginName=" + loginName
				+ ", password=" + password + ", regTime=" + regTime
				+ ", lastLogin=" + lastLogin + "]";
	}
}
