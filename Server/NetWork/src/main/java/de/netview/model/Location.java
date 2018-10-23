package de.netview.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "LOCATION")
public class Location implements Serializable {

	public Long lid;
	public String displayname;
	public String street;
	public String plz;
	public String city;
	public String country;
	
	@Id
    @GeneratedValue
    @Column(name = "lid", nullable = false)
	public Long getLid() {
		return lid;
	}
	
	public void setLid(Long lid) {
		this.lid = lid;
	}
	
	public String getDisplayname() {
		return displayname;
	}

	public void setDisplayname(String displayname) {
		this.displayname = displayname;
	}

	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getPlz() {
		return plz;
	}
	public void setPlz(String plz) {
		this.plz = plz;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
	
}
