package de.netview.dao;

import java.util.List;

import de.netview.model.MobileUser;

public interface IMobileUserDao {

	public void addMobileUser(MobileUser mobileUser);
	public void updateMobileUser(MobileUser mobileUser);
	public void deleteMobileUser(Long muid);
	public List getMobileUser();
	public MobileUser getMobileUserByID(Long muid);
}
