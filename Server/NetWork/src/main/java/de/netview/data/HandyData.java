package de.netview.data;

import de.netview.model.Handy;
import de.netview.model.HandyModel;
import de.netview.model.MobileUser;

import javax.persistence.*;

public class HandyData {

    private Long hid;
    private String imei;
    private String deliveryDate;
    private String description;
    private HandyModelData handyModel;

    public HandyData(Handy handy) {
        this.setHid(handy.getHid());
        this.setImei(handy.getImei());
        this.setDeliveryDate(handy.getDeliveryDate());
        this.setDescription(handy.getDescription());
        this.setHandyModel(new HandyModelData(handy.getHandyModel()));
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public HandyModelData getHandyModel() {
        return handyModel;
    }

    public void setHandyModel(HandyModelData handyModel) {
        this.handyModel = handyModel;
    }

    public Long getHid() {
        return hid;
    }

    public void setHid(Long hid) {
        this.hid = hid;
    }
}
