package de.netview.service.job.impl;

import java.util.List;

import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import de.netview.config.BeanUtil;
import de.netview.config.HibernateUtil;
import de.netview.function.IHardwareCheck;
import de.netview.model.Hardware;
import de.netview.service.IHardwareService;
import de.netview.service.Impl.HardwareService;

public class CheckHardwareJob extends AJob {


	private SessionFactory sessionFactory;

	private IHardwareCheck hardwareCheck;

	private IHardwareService hardwareService;

	public CheckHardwareJob() {
		sessionFactory = BeanUtil.getBean(SessionFactory.class);
		hardwareService = BeanUtil.getBean(IHardwareService.class);
		hardwareCheck = BeanUtil.getBean(IHardwareCheck.class);
    }
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		Session session = sessionFactory.openSession();
		
		try {
            TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(session));
            
            System.out.println("####### Start Check Hardware");
            
            List<Hardware> hardwareList = hardwareService.getAllHardware();
            for (Hardware hardware : hardwareList) {
            	hardwareCheck.checkHostname(hardware);
            }
            
            System.out.println("####### End Check Hardware - Erfolgreich");
            

        } catch (Exception error) {
        	System.out.println("####### End Check Hardware - FEHLER");
        	error.printStackTrace();            
        } finally {
            session.close();
            TransactionSynchronizationManager.unbindResource(sessionFactory);
        }
		
	}

}
