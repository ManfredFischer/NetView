package de.netview.dao;

import java.util.List;

import de.netview.model.Handy;

public interface IHandyDao {
	
	public void addHandy(Handy handy);
	public void updateHandy(Handy handy);
	public void removeHandy(Long hid);
	public List getHandy();
	public Handy getHandyById(Long hid);

}
