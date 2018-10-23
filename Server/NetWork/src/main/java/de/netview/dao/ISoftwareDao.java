package de.netview.dao;

import de.netview.model.Software;

public interface ISoftwareDao {

	public void insertSoftware(Software software);
	public Software getSoftwareByName(String name);
}
