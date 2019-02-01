package de.netview.service;

import java.util.List;

import de.netview.data.LizenzInformation;
import de.netview.model.Lizenz;

public interface ILizenzService {
	
	public void insertAndCheckLizenz(Lizenz lizenz);
	public void insertLizenz(Lizenz lizenz);
	public List<LizenzInformation> getLizenz(String state);
	public void checkLizenz(Lizenz lizenz, int modus);
	public void updateLizenz(Lizenz lizenz);
	public void deleteLizenz(Long lid);
	public Lizenz getLizenzById(Long lid);
	public Lizenz getLizenzByNameAndKey(String Name, String Key);
	Lizenz updateLizenzState(Lizenz lizenz);

}
