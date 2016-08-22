package de.netview.model;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by mf on 21.08.2016.
 */
@Entity
@Table(name = "Protokoll")
public class Protokoll {

    private Long pid;
    private String created;
    private String description;
    private Boolean deleted;

    private Set<User> users = new HashSet<>();
    private Set<Location> locations = new HashSet<>();
    private Set<Network> networks = new HashSet<>();
    private Set<Hardware> hardwares = new HashSet<>();

    @Id
    @GeneratedValue
    @Column(name = "pid", nullable = false)
    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "UserProtokoll",
            joinColumns = {@JoinColumn(name = "pid")},
            inverseJoinColumns = {@JoinColumn(name = "uid")})
    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "LocationProtokoll",
            joinColumns = {@JoinColumn(name = "pid")},
            inverseJoinColumns = {@JoinColumn(name = "lid")})
    public Set<Location> getLocations() {
        return locations;
    }

    public void setLocations(Set<Location> locations) {
        this.locations = locations;
    }

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "LocationProtokoll",
            joinColumns = {@JoinColumn(name = "pid")},
            inverseJoinColumns = {@JoinColumn(name = "lid")})
    public Set<Network> getNetworks() {
        return networks;
    }

    public void setNetworks(Set<Network> networks) {
        this.networks = networks;
    }

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "HardwareProtokoll",
            joinColumns = {@JoinColumn(name = "pid")},
            inverseJoinColumns = {@JoinColumn(name = "hid")})
    public Set<Hardware> getHardwares() {
        return hardwares;
    }

    public void setHardwares(Set<Hardware> hardwares) {
        this.hardwares = hardwares;
    }
}
