package de.netview.service.Impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.netview.dao.IContractDao;
import de.netview.model.Contract;
import de.netview.service.IContractService;

@Service
public class ContractService implements IContractService {
	
	@Autowired
	private IContractDao contractDao;

	@Override
	@Transactional
	public Contract addContract(Contract contract) {
		contractDao.addContract(contract);
		return contract;
	}

	@Override
	@Transactional
	public Contract updateContract(Contract contract) {
		contractDao.updateContract(contract);
		return contract;
	}

	@Override
	@Transactional
	public void deleteContract(Long cid) {
		contractDao.deleteContract(cid);
	}

	@Override
	@Transactional
	public List getContract() {
		return contractDao.getContract();
	}

	@Override
	@Transactional
	public Contract getContractById(Long cid) {
		return contractDao.getContractById(cid);
	}

}
