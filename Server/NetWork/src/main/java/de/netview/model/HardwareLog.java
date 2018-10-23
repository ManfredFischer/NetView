package de.netview.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "HARDWARELOG")
public class HardwareLog {
	
	private long hpid;
	private String eventId;
	private String category;
	private String message;
	private String source;
	private String timeGenerated;
	private String timeWritten;
	private String username;
	private String hostname;
	
	@Id
    @GeneratedValue
    @Column(name = "hpid", nullable = false)
	public long getHpid() {
		return hpid;
	}
	public void setHpid(long hpid) {
		this.hpid = hpid;
	}
	public String getEventId() {
		return eventId;
	}
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getTimeGenerated() {
		return timeGenerated;
	}
	public void setTimeGenerated(String timeGenerated) {
		this.timeGenerated = timeGenerated;
	}
	public String getTimeWritten() {
		return timeWritten;
	}
	public void setTimeWritten(String timeWritten) {
		this.timeWritten = timeWritten;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getHostname() {
		return hostname;
	}
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

}
