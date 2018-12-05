package de.netview.service;

import java.util.List;

import de.netview.model.Lizenz;

public interface ILizenzService {
	
	public void insertLizenz(Lizenz lizenz);
	public List<Lizenz> getLizenz();
	public void checkLizenz(Lizenz lizenz);

}
