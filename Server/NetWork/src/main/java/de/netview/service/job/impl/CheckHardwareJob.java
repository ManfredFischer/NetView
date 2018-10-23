package de.netview.service.job.impl;

import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import de.netview.config.BeanUtil;
import de.netview.service.IHardwareService;
import de.netview.service.Impl.HardwareService;

public class CheckHardwareJob implements Job {

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		System.out.println("Check Hardware");
	}

}
