package de.netview.model;

import java.sql.Blob;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CONVERTJOB")
public class ConvertJob {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false, precision = 10)
	private Long cjid;
	
	private String name;
	private String status;
	private Date date;
	private byte[] input;
	private byte[] output;
	
	public Long getCjid() {
		return cjid;
	}
	public void setCjid(Long cjid) {
		this.cjid = cjid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public byte[] getInput() {
		return input;
	}
	public void setInput(byte[] input) {
		this.input = input;
	}
	public byte[] getOutput() {
		return output;
	}
	public void setOutput(byte[] output) {
		this.output = output;
	}
	
	
	
	

}
