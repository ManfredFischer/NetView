package de.netview.dao;

import java.util.List;

import de.netview.model.HandyModel;

public interface IHandyModelDao {
	
	public void addHandyModel(HandyModel handyModel);
	public void updateHandyModel(HandyModel handyModel);
	public void removeHandyModel(Long hmid);
	public List getHandyModel();
	public HandyModel getHandyModelById(Long hmid);

}
