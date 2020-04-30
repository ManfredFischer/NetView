package de.netview.model;

public class Netz {

    private Long nid;
    private Long lid;
    private String displayname;
    private String subnetz;
    private String netmask;
    private String clientsamount;
    private String gateway;
    private String vlan;
    private String description;

    public Long getNid() {
        return nid;
    }

    public void setNid(Long nid) {
        this.nid = nid;
    }

    public Long getLid() {
        return lid;
    }

    public void setLid(Long lid) {
        this.lid = lid;
    }

    public String getDisplayname() {
        return displayname;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }

    public String getSubnetz() {
        return subnetz;
    }

    public void setSubnetz(String subnetz) {
        this.subnetz = subnetz;
    }

    public String getNetmask() {
        return netmask;
    }

    public void setNetmask(String netmask) {
        this.netmask = netmask;
    }

    public String getClientsamount() {
        return clientsamount;
    }

    public void setClientsamount(String clientsamount) {
        this.clientsamount = clientsamount;
    }

    public String getGateway() {
        return gateway;
    }

    public void setGateway(String gateway) {
        this.gateway = gateway;
    }

    public String getVlan() {
        return vlan;
    }

    public void setVlan(String vlan) {
        this.vlan = vlan;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
