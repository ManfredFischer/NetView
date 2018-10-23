package de.netview.dao;

import java.util.List;

import de.netview.model.Lizenz;

public interface ILizenzDao {
	public void insertLizenz(Lizenz hardwareLizenz);
	public String checkLizenzByHostnameAndLizenz(Lizenz hardwareLizenz);
}
