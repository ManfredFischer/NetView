package de.netview.config;

import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.sql.DataSource;
import javax.transaction.TransactionManager;

import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.jdbcjobstore.DB2v8Delegate;
import org.quartz.impl.jdbcjobstore.PostgreSQLDelegate;
import org.quartz.impl.jdbcjobstore.oracle.OracleDelegate;
import org.quartz.simpl.RAMJobStore;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.quartz.LocalDataSourceJobStore;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import de.netview.service.job.impl.SingleJobListener;

@Configuration
@EnableAsync
@EnableScheduling
public class SchedulerConfiguration implements SchedulingConfigurer, AsyncConfigurer {

	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		taskRegistrar.setScheduler(taskScheduler());
	}

	@Override
	public Executor getAsyncExecutor() {
		return taskScheduler();
	}

	@Autowired
	@Bean
	public SchedulerFactoryBean scheduler(ApplicationContext applicationContext, DataSource dataSource)
			throws Exception {

		SchedulerFactoryBean scheduler = new SchedulerFactoryBean();

		scheduler.setApplicationContext(applicationContext);
		scheduler.setWaitForJobsToCompleteOnShutdown(true);
		scheduler.setOverwriteExistingJobs(true);
		scheduler.setJobFactory(new SpringBeanJobFactory());
		scheduler.setDataSource(dataSource);
		scheduler.setExposeSchedulerInRepository(true);
		scheduler.setDataSource(null);

		Properties quartzProperties = new Properties();

		quartzProperties.put("org.quartz.jobStore.class", RAMJobStore.class.getName());

		// misfire
		quartzProperties.put("org.quartz.jobStore.misfireThreshold", "6000000");

		// thread pool
		quartzProperties.put("org.quartz.threadPool.threadCount", Integer.toString(8));
		quartzProperties.put("org.quartz.threadPool.threadPriority", "1");

		// listeners
		quartzProperties.put("org.quartz.jobListener.SingleJobListener.class", SingleJobListener.class.getName());

		scheduler.setQuartzProperties(quartzProperties);

		return scheduler;

	}

	@Bean(destroyMethod = "shutdown")
	public Executor taskExecutor() {
		return Executors.newCachedThreadPool();
	}

	@Bean(destroyMethod = "shutdown")
	public Executor taskScheduler() {
		return Executors.newScheduledThreadPool(10);
	}

	@Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		return new SimpleAsyncUncaughtExceptionHandler();
	}
}
