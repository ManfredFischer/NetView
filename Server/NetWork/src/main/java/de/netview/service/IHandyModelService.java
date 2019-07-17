package de.netview.service;

import java.util.List;

import de.netview.model.HandyModel;

public interface IHandyModelService {

	public HandyModel addHandyModel(HandyModel handyModel);
	public HandyModel updateHandyModel(HandyModel handyModel);
	public void removeHandyModel(Long hmid);
	public List getHandyModel();
	public HandyModel getHandyModelById(Long hmid);
}
