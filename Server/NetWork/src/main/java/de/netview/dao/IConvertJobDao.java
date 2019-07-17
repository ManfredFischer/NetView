package de.netview.dao;

import java.util.List;

import de.netview.model.ConvertJob;

public interface IConvertJobDao {
	
	public void addConvertJob(ConvertJob convertJob);
	public void updateConvertJob(ConvertJob convertJob);
	public void removeConvertJob(ConvertJob cjid);
	public List getConvertJob();
	public ConvertJob getHandyById(ConvertJob cjid);

}
