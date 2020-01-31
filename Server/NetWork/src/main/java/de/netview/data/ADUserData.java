package de.netview.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ADUserData implements Serializable {

	private String uid;
	private String displayName;
    private String firstname;
    private String lastname;
    private String department;
    private String mail;
    private String otherTelepone;
    private String telephoneNumber;
    private String title;
    private String plz;
    private String mobile;
    private String streetAddress;
    private String country;
    private String city;
    private HardwareInformation hardware;
    private Long lid = -1L;


	public Long getLid() {
		return lid;
	}

	public void setLid(Long lid) {
		this.lid = lid;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}
	
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }


    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }


    public String getOtherTelepone() {
        return otherTelepone;
    }

    public void setOtherTelepone(String otherTelepone) {
        this.otherTelepone = otherTelepone;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPlz() {
        return plz;
    }

    public void setPlz(String plz) {
        this.plz = plz;
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

	public HardwareInformation getHardware() {
		return hardware;
	}

	public void setHardware(HardwareInformation hardware) {
		this.hardware = hardware;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}
}
