package de.netview.dao;

import java.util.List;

import de.netview.model.Lizenz;

public interface ILizenzDao  {
	public void insertLizenz(Lizenz lizenz);
	public void updateLizenz(Lizenz lizenz);
	public Lizenz getLizenzByName(String name, String key);
	public List<Lizenz> getLizenz(String state);
	public int getLizenzState(Lizenz lizenz);
	public void deleteLizenz(Lizenz lizenz);
	public Lizenz getLizenzById(Long lid);
}
