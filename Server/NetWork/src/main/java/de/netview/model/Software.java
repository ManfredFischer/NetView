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
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "SOFTWARE")
public class Software implements Serializable{
	
	private long sid;
	private String name;
	private List<Hardware> hardware = new ArrayList<Hardware>();
	
	public Software() {
		
	}
	
	public Software(String name) {
		this.name = name;
	}
	
	
	@ManyToMany(fetch = FetchType.LAZY,
    cascade = {
        CascadeType.PERSIST,
        CascadeType.MERGE
    },
    mappedBy = "software")
	@JsonIgnore
	public List<Hardware> getHardware() {
		return hardware;
	}
	
	@JsonIgnore
	public void setHardware(List<Hardware> hardware) {
		this.hardware = hardware;
	}
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false, precision = 10)
	public long getSid() {
		return sid;
	}
	
	public void setSid(long sid) {
		this.sid = sid;
	}
	

    @Column(unique = true)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	

}
