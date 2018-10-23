package de.netview.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "SYSTEMUSER")
public class Systemuser {


    private Long uid;
    private String username;
    private String password;

    @Id
    @GeneratedValue
    @Column(name = "uid", nullable = false)
    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
