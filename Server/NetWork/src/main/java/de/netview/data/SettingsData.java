package de.netview.data;

import java.io.Serializable;
import java.util.List;

import de.netview.model.ADSetting;

public class SettingsData implements Serializable {

	private static final long serialVersionUID = -2029669193487124537L;
	private ADSetting ad;

	public ADSetting getAd() {
		return ad;
	}

	public void setAd(ADSetting ad) {
		this.ad = ad;
	}

}
