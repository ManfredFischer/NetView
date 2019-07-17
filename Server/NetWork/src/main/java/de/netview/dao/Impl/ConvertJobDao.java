package de.netview.dao.Impl;

import java.util.List;

import org.springframework.stereotype.Component;

import de.netview.dao.IConvertJobDao;
import de.netview.dao.config.AbstractDao;
import de.netview.model.Contract;
import de.netview.model.ConvertJob;

@Component
public class ConvertJobDao extends AbstractDao<ConvertJobDao> implements IConvertJobDao {

	@Override
	public void addConvertJob(ConvertJob convertJob) {
		getSession().save(convertJob);
		getSession().flush();
	}

	@Override
	public void updateConvertJob(ConvertJob convertJob) {
		getSession().update(convertJob);		
	}

	@Override
	public void removeConvertJob(ConvertJob cjid) {
		getSession().delete(cjid);
	}

	@Override
	public List getConvertJob() {
		return getSession().createQuery("from convertjob").list();
	}

	@Override
	public ConvertJob getHandyById(ConvertJob cjid) {
		return (ConvertJob) getSession().createQuery("from Contract where cjid = :cjid").setParameter("cjid", cjid).uniqueResult();
	}

}
