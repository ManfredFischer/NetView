package de.netview.data;

import de.netview.model.HandyModel;

import java.io.Serializable;

public class HandyModelData implements Serializable {

    private Long hmid;
    private String displayName;
    private String model;
    private String color;
    private String store;
    private Boolean selectable;

    public HandyModelData(HandyModel handyModel) {
        this.setHmid(handyModel.getHmid());
        this.setDisplayName(handyModel.getDisplayName());
        this.setModel(handyModel.getModel());
        this.setColor(handyModel.getColor());
        this.setSelectable(handyModel.isSelectable());
        this.setStore(handyModel.getStore());
    }

    public Long getHmid() {
        return hmid;
    }

    public void setHmid(Long hmid) {
        this.hmid = hmid;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public Boolean getSelectable() {
        return selectable;
    }

    public void setSelectable(Boolean selectable) {
        this.selectable = selectable;
    }
}
