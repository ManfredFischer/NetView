package de.netview.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "HANDY")
public class Handy implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4508919623837178661L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false, precision = 10)
	private Long hid;
	private String imei;
	private String deliveryDate;
	private String description;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="hmid")
	private HandyModel handyModel;
	
	@OneToOne(mappedBy="handy")
	private MobileUser mobileOwner;
	
	public Long getHid() {
		return hid;
	}
	public void setHid(Long hid) {
		this.hid = hid;
	}
	public HandyModel getHandyModel() {
		return handyModel;
	}
	public void setHandyModel(HandyModel handyModel) {
		this.handyModel = handyModel;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public String getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public MobileUser getMobileOwner() {
		return mobileOwner;
	}
	public void setMobileOwner(MobileUser mobileOwner) {
		this.mobileOwner = mobileOwner;
	}
	
	
	
	
	
}
