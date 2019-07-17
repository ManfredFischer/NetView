package de.netview.dao;

import java.util.List;

import de.netview.model.Contract;

public interface IContractDao {

	public void addContract(Contract contract);
	public void updateContract(Contract contract);
	public void deleteContract(Long cid);
	public List getContract();
	public Contract getContractById(Long cid);
}
