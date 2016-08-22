package de.netview.model;

import com.sun.javafx.beans.IDProperty;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by mf on 21.08.2016.
 */
@Entity
@Table(name ="Network")
public class Network {
    private String nid;
    private String name;
    private String subnetz;
    private String mask;
    private String description;
    private Boolean removed;

    private Set<Location> locations  = new HashSet<>();
    private Set<Hardware> hardwares = new HashSet<>();
    private Set<Protokoll> protokolls = new HashSet<>();

    @Id
    @GeneratedValue
    @Column(name = "nid", nullable = false)
    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "NetworkLocation",
            joinColumns = {@JoinColumn(name = "nid")},
            inverseJoinColumns = {@JoinColumn(name = "lid")})
    public Set<Location> getLocations() {
        return locations;
    }

    public void setLocations(Set<Location> locations) {
        this.locations = locations;
    }

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "network")
    public Set<Hardware> getHardwares() {
        return hardwares;
    }

    public void setHardwares(Set<Hardware> hardwares) {
        this.hardwares = hardwares;
    }

    public Boolean getRemoved() {
        return removed;
    }

    public void setRemoved(Boolean removed) {
        this.removed = removed;
    }

    public String getMask() {
        return mask;
    }

    public void setMask(String mask) {
        this.mask = mask;
    }

    public String getSubnetz() {
        return subnetz;
    }

    public void setSubnetz(String subnetz) {
        this.subnetz = subnetz;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "NetworkProtokoll",
            joinColumns = {@JoinColumn(name = "nid")},
            inverseJoinColumns = {@JoinColumn(name = "pid")})
    public Set<Protokoll> getProtokolls() {
        return protokolls;
    }

    public void setProtokolls(Set<Protokoll> protokolls) {
        this.protokolls = protokolls;
    }
}
