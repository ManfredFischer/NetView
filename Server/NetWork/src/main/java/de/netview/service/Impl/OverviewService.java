package de.netview.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.netview.data.Overview;
import de.netview.service.IHardwareService;
import de.netview.service.IOverviewService;

@Service
public class OverviewService implements IOverviewService{

	@Autowired
	private IHardwareService hardwareService;
	
	
	@Override
	public Overview getOverviewInformation() {
		Overview overview = new Overview();
		
		
		
		return null;
	}
	
	

}
