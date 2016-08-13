package de.netview.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "User")
public class User {


    private Long uid;
    private String username;
    private String lastname;
    private String firstname;
    private String personalNr;
    private String function;
    private String phone;
    private String handy;
    private String fax;
    private String department;
    private String email;
    private List<Group> group = new ArrayList<Group>();

    @Id
    @GeneratedValue
    @Column(name = "uid", nullable = false)
    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }


    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getPersonalNr() {
        return personalNr;
    }

    public void setPersonalNr(String personalNr) {
        this.personalNr = personalNr;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getHandy() {
        return handy;
    }

    public void setHandy(String handy) {
        this.handy = handy;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(name="GroupUser", joinColumns=@JoinColumn(name="uid"), inverseJoinColumns=@JoinColumn(name="gid"))
    public List<Group> getGroup() {
        return group;
    }

    public void setGroup(List<Group> group) {
        this.group = group;
    }

    @Column(name = "username", nullable = false)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
