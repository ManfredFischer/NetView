package de.netview.data;

import java.io.Serializable;

import de.netview.model.Lizenz;

public class LizenzData implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2333159285303268420L;
	private long lid;
	private String name;
	private String key;
	private long state;
	private Integer allowamount;
	private String categorie;
	
	public LizenzData() {
		
	};
	
	public LizenzData(Lizenz lizenz) {
		this.setLid(lizenz.getLid());
		this.setName(lizenz.getName());
		this.setKey(lizenz.getKey());
		this.setCategorie(lizenz.getCategorie());
		this.setState(lizenz.getState());
		this.setAllowamount(lizenz.getAllowamount());
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
	
	
	
	

}
