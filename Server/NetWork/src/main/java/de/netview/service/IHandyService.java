package de.netview.service;

import java.util.List;

import de.netview.model.Handy;

public interface IHandyService {


	public Handy addHandy(Handy handy);
	public Handy updateHandy(Handy handy);
	public void removeHandy(Long hid);
	public List getHandy();
	public Handy getHandyById(Long hid);
}
