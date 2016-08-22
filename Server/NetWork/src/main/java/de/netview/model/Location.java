package de.netview.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by mf on 20.08.2016.
 */
@Entity
@Table(name = "Location")
public class Location {

    private Long lid;
    private String name;
    private String company;
    private String street;
    private String plz;
    private String city;
    private String description;
    private Boolean removed;


    private Set<User> users = new HashSet<>();
    private Set<Network> netzworks = new HashSet<>();
    private Set<Protokoll> protokolls = new HashSet<>();

    @Id
    @GeneratedValue
    @Column(name = "lid", nullable = false)
    public Long getLid() {
        return lid;
    }

    public void setLid(Long lid) {
        this.lid = lid;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "LocationUser",
            joinColumns = {@JoinColumn(name = "lid")},
            inverseJoinColumns = {@JoinColumn(name = "uid")})
    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public String getPlz() {
        return plz;
    }

    public void setPlz(String plz) {
        this.plz = plz;
    }

    public Boolean getRemoved() {
        return removed;
    }

    public void setRemoved(Boolean removed) {
        this.removed = removed;
    }

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "NetworkLocation",
            joinColumns = {@JoinColumn(name = "lid")},
            inverseJoinColumns = {@JoinColumn(name = "nid")})
    public Set<Network> getNetzworks() {
        return netzworks;
    }

    public void setNetzworks(Set<Network> netzworks) {
        this.netzworks = netzworks;
    }

    public void addNetwork(Network network){
        this.netzworks.add(network);
    }

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "LocationProtokoll",
            joinColumns = {@JoinColumn(name = "lid")},
            inverseJoinColumns = {@JoinColumn(name = "pid")})
    public Set<Protokoll> getProtokolls() {
        return protokolls;
    }

    public void setProtokolls(Set<Protokoll> protokolls) {
        this.protokolls = protokolls;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
