package de.netview.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "MOBILEUSER")
public class MobileUser implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4722522167294441470L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false, precision = 10)
	private Long mid;
	private String name;
	private String number;
	private String sim;
	private String pin;
	private String puk;
	private String ultraOne;
	private String ultraTwo;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="hid")
	private Handy handy;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="cid")
	private Contract contract;
	
	public Long getMid() {
		return mid;
	}
	public void setMid(Long mid) {
		this.mid = mid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getSim() {
		return sim;
	}
	public void setSim(String sim) {
		this.sim = sim;
	}
	public String getPin() {
		return pin;
	}
	public void setPin(String pin) {
		this.pin = pin;
	}
	public String getPuk() {
		return puk;
	}
	public void setPuk(String puk) {
		this.puk = puk;
	}
	public String getUltraOne() {
		return ultraOne;
	}
	public void setUltraOne(String ultraOne) {
		this.ultraOne = ultraOne;
	}
	public String getUltraTwo() {
		return ultraTwo;
	}
	public void setUltraTwo(String ultraTwo) {
		this.ultraTwo = ultraTwo;
	}
	public Handy getHandy() {
		return handy;
	}
	public void setHandy(Handy handy) {
		this.handy = handy;
	}
	public Contract getContract() {
		return contract;
	}
	public void setContract(Contract contract) {
		this.contract = contract;
	}
	
	

}
