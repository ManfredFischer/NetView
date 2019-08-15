package de.netview.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import de.netview.model.Hardware;
import de.netview.model.Lizenz;

public class LizenzInformation  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2333159285303268420L;
	private long lid;
	private String name;
	private String key;
	private long state;
	private String reserved;
	private String categorie;
	private Integer allowamount;
	private List<HardwareInformation> hardware = new ArrayList<>();
	
	public LizenzInformation(Lizenz lizenz) {
		this.categorie = lizenz.getCategorie();
		this.name = lizenz.getName();
		this.key = lizenz.getKey();
		this.state = lizenz.getState();
		this.lid = lizenz.getLid();
		this.reserved = lizenz.getReserved();
		this.allowamount = lizenz.getAllowamount();
		
		
		for (Hardware hardwareData : lizenz.getHardware()) {
			this.hardware.add(new HardwareInformation(hardwareData));
		}
	}


	public Integer getAllowamount() {
		return allowamount;
	}

	public void setAllowamount(Integer allowamount) {
		this.allowamount = allowamount;
	}

	public long getLid() {
		return lid;
	}
	public void setLid(long lid) {
		this.lid = lid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public long getState() {
		return state;
	}
	public void setState(long state) {
		this.state = state;
	}
	public String getCategorie() {
		return categorie;
	}
	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}
	public List<HardwareInformation> getHardware() {
		return hardware;
	}
	public void setHardware(List<HardwareInformation> hardware) {
		this.hardware = hardware;
	}

	public String getReserved() {
		return reserved;
	}

	public void setReserved(String reserved) {
		this.reserved = reserved;
	}
	
	

	
}
