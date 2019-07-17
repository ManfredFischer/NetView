package de.netview.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "HANDYMODEL")
public class HandyModel implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false, precision = 10)
	private Long hmid;
	private String displayName;
	private String model;
	private String color;
	private String store;
	private Boolean selectable;
	
	@OneToMany(mappedBy="handyModel")
	@JsonIgnore
	private List<Handy> listHandy = new ArrayList();
	
	public Long getHmid() {
		return hmid;
	}
	public void setHmid(Long hmid) {
		this.hmid = hmid;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getStore() {
		return store;
	}
	public void setStore(String store) {
		this.store = store;
	}
	public List<Handy> getListHandy() {
		return listHandy;
	}
	public void setListHandy(List<Handy> listHandy) {
		this.listHandy = listHandy;
	}
	public Boolean isSelectable() {
		return selectable;
	}
	public void setSelectable(Boolean selectable) {
		this.selectable = selectable;
	}
	
	
	

}
