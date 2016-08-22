package de.netview.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by mf on 21.08.2016.
 */
@Entity
@Table(name = "Hardware")
public class Hardware {

    private Long hid;
    private String connectionid;
    private String lastlogin;
    private String lastlogout;
    private String group;
    private String inventurnr;
    private String leasingende;
    private String type;
    private String function;
    private String seriennr;
    private String costcenter;
    private String description;
    private Boolean removed;

    private User admin;
    private Network network;
    private Set<Protokoll> protokolls = new HashSet<>();

    @Id
    @GeneratedValue
    @Column(nullable = false, name = "hid")
    public Long getHid() {
        return hid;
    }

    public void setHid(Long hid) {
        this.hid = hid;
    }

    public String getConnectionid() {
        return connectionid;
    }

    public void setConnectionid(String connectionid) {
        this.connectionid = connectionid;
    }

    public String getLastlogin() {
        return lastlogin;
    }

    public void setLastlogin(String lastlogin) {
        this.lastlogin = lastlogin;
    }

    public String getLastlogout() {
        return lastlogout;
    }

    public void setLastlogout(String lastlogout) {
        this.lastlogout = lastlogout;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getInventurnr() {
        return inventurnr;
    }

    public void setInventurnr(String inventurnr) {
        this.inventurnr = inventurnr;
    }

    public String getLeasingende() {
        return leasingende;
    }

    public void setLeasingende(String leasingende) {
        this.leasingende = leasingende;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public String getSeriennr() {
        return seriennr;
    }

    public void setSeriennr(String seriennr) {
        this.seriennr = seriennr;
    }

    public String getCostcenter() {
        return costcenter;
    }

    public void setCostcenter(String costcenter) {
        this.costcenter = costcenter;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @ManyToOne
    @JoinColumn(name = "uid", nullable = false)
    public User getAdmin() {
        return admin;
    }

    public void setAdmin(User admin) {
        this.admin = admin;
    }

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "nid", nullable = false)
    public Network getNetwork() {
        return network;
    }

    public void setNetwork(Network network) {
        this.network = network;
    }

    public Boolean getRemoved() {
        return removed;
    }

    public void setRemoved(Boolean removed) {
        this.removed = removed;
    }

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "HardwareProtokoll",
            joinColumns = {@JoinColumn(name = "hid")},
            inverseJoinColumns = {@JoinColumn(name = "pid")})
    public Set<Protokoll> getProtokolls() {
        return protokolls;
    }

    public void setProtokolls(Set<Protokoll> protokolls) {
        this.protokolls = protokolls;
    }
}
