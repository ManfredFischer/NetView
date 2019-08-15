package de.netview.data;

import de.netview.model.Contract;

import java.io.Serializable;

public class ContractData implements Serializable {

    private Long cid;
    private String name;
    private String cost;
    private String discount;
    private String description;
    private String contract;
    private Boolean enable;

    public ContractData(Contract contract) {
        this.setCid(contract.getCid());
        this.setName(contract.getName());
        this.setCost(contract.getCost());
        this.setDiscount(contract.getDiscount());
        this.setDescription(contract.getDescription());
        this.setContract(contract.getContract());
        this.setEnable(contract.getEnable());
    }

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContract() {
        return contract;
    }

    public void setContract(String contract) {
        this.contract = contract;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }
}
