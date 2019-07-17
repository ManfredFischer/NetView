package de.netview.dao.Impl;

import java.util.List;

import org.springframework.stereotype.Component;

import de.netview.dao.IContractDao;
import de.netview.dao.config.AbstractDao;
import de.netview.model.Contract;

@Component
public class ContractDao extends AbstractDao<Contract> implements IContractDao {

	@Override
	public void addContract(Contract contract) {
		getSession().save(contract);
	}

	@Override
	public void updateContract(Contract contract) {
		getSession().update(contract);
		
	}

	@Override
	public void deleteContract(Long cid) {
		getSession().delete(getContractById(cid));
		
	}

	@Override
	public List getContract() {
		return getSession().createQuery("from Contract").list();
	}

	@Override
	public Contract getContractById(Long cid) {
		return (Contract) getSession().createQuery("from Contract where cid = :cid").setParameter("cid", cid).uniqueResult();
	}

}
