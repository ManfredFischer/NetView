package de.netview.service;

import java.util.List;

import de.netview.model.MobileUser;

public interface IMobileUserService {

	public MobileUser addMobileUser(MobileUser mobileUser);
	public MobileUser updateMobileUser(MobileUser mobileUser);
	public void deleteMobileUser(Long muid);
	public List getMobileUser();
	public MobileUser getMobileUserByID(Long muid);
	
}
