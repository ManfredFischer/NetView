package de.netview.data;

import java.io.Serializable;

public class ADUserUpdateData implements Serializable {
	private String department;
	private String firstname;
	private String lastname;
	private String position;
	private Long lid;
	private String uid;
	private String mobile;
	private String tel;
	private String otel;
	
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getOtel() {
		return otel;
	}
	public void setOtel(String otel) {
		this.otel = otel;
	}
	public String getDepartment() {
		return department;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public Long getLid() {
		return lid;
	}
	public void setLid(Long lid) {
		this.lid = lid;
	}
	
	
	
	

}
