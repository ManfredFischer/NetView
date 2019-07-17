package de.netview.data;

import java.io.Serializable;
import java.util.List;

public class Overview implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8873854039173253074L;
	
	private String title;
	private Long adUserAmount;
	
	
	private Long rentHardwareAmount;
	private List rentHardwareList;
	
	private Long hardwareAmount;
	private Long hardwareDisableAmount;
	private List hardwareDisableList;
	
	private Long lizenzAmount;
	private Long lizenzDuplicateAmount;
	private Long lizenzErrorAmount;
	private List lizenzErrorList;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Long getAdUserAmount() {
		return adUserAmount;
	}
	public void setAdUserAmount(Long adUserAmount) {
		this.adUserAmount = adUserAmount;
	}
	public Long getRentHardwareAmount() {
		return rentHardwareAmount;
	}
	public void setRentHardwareAmount(Long rentHardwareAmount) {
		this.rentHardwareAmount = rentHardwareAmount;
	}
	public List getRentHardwareList() {
		return rentHardwareList;
	}
	public void setRentHardwareList(List rentHardwareList) {
		this.rentHardwareList = rentHardwareList;
	}
	public Long getHardwareAmount() {
		return hardwareAmount;
	}
	public void setHardwareAmount(Long hardwareAmount) {
		this.hardwareAmount = hardwareAmount;
	}
	public Long getHardwareDisableAmount() {
		return hardwareDisableAmount;
	}
	public void setHardwareDisableAmount(Long hardwareDisableAmount) {
		this.hardwareDisableAmount = hardwareDisableAmount;
	}
	public List getHardwareDisableList() {
		return hardwareDisableList;
	}
	public void setHardwareDisableList(List hardwareDisableList) {
		this.hardwareDisableList = hardwareDisableList;
	}
	public Long getLizenzAmount() {
		return lizenzAmount;
	}
	public void setLizenzAmount(Long lizenzAmount) {
		this.lizenzAmount = lizenzAmount;
	}
	public Long getLizenzDuplicateAmount() {
		return lizenzDuplicateAmount;
	}
	public void setLizenzDuplicateAmount(Long lizenzDuplicateAmount) {
		this.lizenzDuplicateAmount = lizenzDuplicateAmount;
	}
	public Long getLizenzErrorAmount() {
		return lizenzErrorAmount;
	}
	public void setLizenzErrorAmount(Long lizenzErrorAmount) {
		this.lizenzErrorAmount = lizenzErrorAmount;
	}
	public List getLizenzErrorList() {
		return lizenzErrorList;
	}
	public void setLizenzErrorList(List lizenzErrorList) {
		this.lizenzErrorList = lizenzErrorList;
	}
	
	

}
