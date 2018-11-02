package de.netview.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ADSETTING")
public class ADSetting implements Serializable {
	
	
	private static final long serialVersionUID = 1237907723340450059L;
	private long sid;
	private String server;
	private int port;
	private Boolean ssl;
	private String domaine;
	private String binduser;
	private String password;
	private String binduserou;
	private String interneuserou;
	private String internegroupou;
	private String internehardwareou;
	private String interneserverou;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sid", nullable = false)
	public long getSid() {
		return sid;
	}
	public void setSid(long sid) {
		this.sid = sid;
	}
	public String getServer() {
		return server;
	}
	public void setServer(String server) {
		this.server = server;
	}
	public String getDomaine() {
		return domaine;
	}
	public void setDomaine(String domaine) {
		this.domaine = domaine;
	}
	public String getBinduser() {
		return binduser;
	}
	public void setBinduser(String binduser) {
		this.binduser = binduser;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getBinduserou() {
		return binduserou;
	}
	public void setBinduserou(String binduserou) {
		this.binduserou = binduserou;
	}
	public String getInterneuserou() {
		return interneuserou;
	}
	public void setInterneuserou(String interneuserou) {
		this.interneuserou = interneuserou;
	}
	public String getInternegroupou() {
		return internegroupou;
	}
	public void setInternegroupou(String internegroupou) {
		this.internegroupou = internegroupou;
	}
	public String getInternehardwareou() {
		return internehardwareou;
	}
	public void setInternehardwareou(String internehardwareou) {
		this.internehardwareou = internehardwareou;
	}
	public Boolean getSsl() {
		return ssl;
	}
	public void setSsl(Boolean ssl) {
		this.ssl = ssl;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getInterneserverou() {
		return interneserverou;
	}
	public void setInterneserverou(String interneserverou) {
		this.interneserverou = interneserverou;
	}
	
	
	
	
	
}
