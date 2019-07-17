package de.netview.service;

import java.util.List;

import de.netview.model.Contract;

public interface IContractService {

	public Contract addContract(Contract contract);
	public Contract updateContract(Contract contract);
	public void deleteContract(Long cid);
	public List getContract();
	public Contract getContractById(Long cid);
}
