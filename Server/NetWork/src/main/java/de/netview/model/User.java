package de.netview.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "User")
public class User {


    private Long uid;
    private String username;
    private String password;
    private String lastname;
    private String firstname;
    private String personalnr;
    private String function;
    private String phone;
    private String handy;
    private String fax;
    private String department;
    private String email;
    private Long lastlogin;
    private Integer state;
    private boolean active;


    private Set<Profile> profiles = new HashSet<Profile>();
    private Set<Location> locations = new HashSet<Location>();
    private Set<Protokoll> protokolls = new HashSet<>();
    private Set<Hardware> hardwares = new HashSet<>();

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

    @Column(name = "username", nullable = false)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getLastlogin() {
        return lastlogin;
    }

    public void setLastlogin(Long lastlogin) {
        this.lastlogin = lastlogin;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getPersonalnr() {
        return personalnr;
    }

    public void setPersonalnr(String personalnr) {
        this.personalnr = personalnr;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "ProfileUser",
            joinColumns = {@JoinColumn(name = "uid")},
            inverseJoinColumns = {@JoinColumn(name = "pid")})
    public Set<Profile> getProfiles() {
        return profiles;
    }

    public void setProfiles(Set<Profile> profiles) {
        this.profiles = profiles;
    }

    public void addProfile(Profile profile){
      this.profiles.add(profile);
    }

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "LocationUser",
            joinColumns = {@JoinColumn(name = "uid")},
            inverseJoinColumns = {@JoinColumn(name = "lid")})
    public Set<Location> getLocations() {
        return locations;
    }

    public void setLocations(Set<Location> locations) {
        this.locations = locations;
    }

    public void addLocation(Location location){
        this.locations.add(location);
    }

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "UserProtokoll",
            joinColumns = {@JoinColumn(name = "uid")},
            inverseJoinColumns = {@JoinColumn(name = "pid")})
    public Set<Protokoll> getProtokolls() {
        return protokolls;
    }

    public void setProtokolls(Set<Protokoll> protokolls) {
        this.protokolls = protokolls;
    }

    public void addProtokoll(Protokoll protokoll){
        this.protokolls.add(protokoll);
    }
    @OneToMany(cascade = {CascadeType.ALL},mappedBy = "admin")
    public Set<Hardware> getHardwares() {
        return hardwares;
    }

    public void setHardwares(Set<Hardware> hardwares) {
        this.hardwares = hardwares;
    }

    public void addHardware(Hardware hardware){
        this.hardwares.add(hardware);
    }

}
