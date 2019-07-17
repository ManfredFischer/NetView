package de.netview.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import de.netview.model.Contract;
import de.netview.service.IContractService;

@RestController
@RequestMapping("/contract")
public class ContractController {
	
	@Autowired
	private IContractService contractService;
	
	@GetMapping
	private List getContract() {
		return contractService.getContract();
	}
	
	@GetMapping("/{cid}")
	private Contract getContractById(@PathVariable Long cid) {
		return contractService.getContractById(cid);
	}
	
	@PostMapping
	private Contract addContract(@RequestBody Contract contract) {
		return contractService.addContract(contract);
	}
	
	@PutMapping
	private Contract putContract(@RequestBody Contract contract) {
		return contractService.updateContract(contract);
	}
	
	@DeleteMapping("/{cid}")
	private void deleteContract(@PathVariable Long cid) {
		contractService.deleteContract(cid);
	}

}
