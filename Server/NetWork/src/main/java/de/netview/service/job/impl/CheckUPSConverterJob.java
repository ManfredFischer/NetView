package de.netview.service.job.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.orm.hibernate5.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import de.netview.config.BeanUtil;
import de.netview.service.IFileConverter;

public class CheckUPSConverterJob extends AJob{
	
	private SessionFactory sessionFactory;

	private IFileConverter fileConverter;

	public CheckUPSConverterJob() {
		sessionFactory = BeanUtil.getBean(SessionFactory.class);
		fileConverter = BeanUtil.getBean(IFileConverter.class);
    }
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		Session session = sessionFactory.openSession();
		
		try {
            TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(session));
            
            System.out.println("####### Start Check UPS Converter");
            
            fileConverter.checkFolder();
            
            System.out.println("####### End Check UPS Converter - Erfolgreich");
            

        } catch (Exception error) {
        	System.out.println("####### End Check UPS Converter - FEHLER");
        	error.printStackTrace();            
        } finally {
            session.close();
            TransactionSynchronizationManager.unbindResource(sessionFactory);
        }
		
	}
}
