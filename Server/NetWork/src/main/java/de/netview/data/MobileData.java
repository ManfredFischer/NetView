package de.netview.data;

import java.io.Serializable;

public class MobileData implements Serializable {
	
	private static final long serialVersionUID = 1794363155538660267L;
	private String username;
	private String number;
	private String sim;
	private String ultraOne;
	private String ultraTwo;
	private String pin;
	private String puk;
	private String description;
	private String cost;
	private String discount;
	private String gesamt;
	private String handy;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCost() {
		return cost;
	}
	public void setCost(String cost) {
		this.cost = cost;
	}
	public String getDiscount() {
		return discount;
	}
	public void setDiscount(String discount) {
		this.discount = discount;
	}
	public String getGesamt() {
		return gesamt;
	}
	public void setGesamt(String gesamt) {
		this.gesamt = gesamt;
	}
	public String getHandy() {
		return handy;
	}
	public void setHandy(String handy) {
		this.handy = handy;
	}
	
	

}
