package org.sch.issecurity.iam.tools.ACMetricsManager.configuration;

import org.sch.issecurity.iam.tools.ACMetricsManager.dao.*;
import org.sch.issecurity.iam.tools.ACMetricsManager.model.ACMetrics;
import org.sch.issecurity.iam.tools.ACMetricsManager.model.Analyst;
import org.sch.issecurity.iam.tools.ACMetricsManager.model.Application;
import org.sch.issecurity.iam.tools.ACMetricsManager.model.Operation;
import org.sch.issecurity.iam.tools.ACMetricsManager.utlity.AnalystAuthoritiesPopulator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.resource.GzipResourceResolver;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.springframework.web.servlet.resource.ResourceUrlEncodingFilter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Properties;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "org.sch.issecurity.iam.tools.ACMetricsManager")
@Import(value = { LoginSecurityConfig.class })
@EnableTransactionManagement
@EnableSwagger2
public class ACMetricsManagerConfiguration extends WebMvcConfigurerAdapter{

	/*
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");
		registry.viewResolver(viewResolver);
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**").addResourceLocations("/static/");
	}
*/

	@Bean
	public ResourceUrlEncodingFilter resourceUrlEncodingFilter() {
		return new ResourceUrlEncodingFilter();
	}


	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/vendor/**")
				.addResourceLocations("/resources/vendor/")
				.setCachePeriod(0)
				.resourceChain(true)
				.addResolver(new GzipResourceResolver())
				.addResolver(new PathResourceResolver());
	}

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}


	@Bean(name = "messageSource")
	public ReloadableResourceBundleMessageSource getMessageSource() {
		ReloadableResourceBundleMessageSource resource = new ReloadableResourceBundleMessageSource();
		resource.setBasename("classpath:messages");
		resource.setDefaultEncoding("UTF-8");
		return resource;
	}

	@Bean(name = "dataSource")
	public DataSource getDataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		dataSource.setUrl("jdbc:sqlserver://LPFORDIAMORA02:1433;DatabaseName=ACOperation;");
		dataSource.setUsername("IAMDEV");
		dataSource.setPassword("admin123");

		return dataSource;
	}


	private Properties getHibernateProperties() {
		Properties properties = new Properties();
		properties.put("hibernate.show_sql", "true");
		properties.put("hibernate.dialect", "org.hibernate.dialect.SQLServer2008Dialect");
		return properties;
	}

	@Autowired
	@Bean(name = "sessionFactory")
	public SessionFactory getSessionFactory(DataSource dataSource) {
		LocalSessionFactoryBuilder sessionBuilder = new LocalSessionFactoryBuilder(dataSource);
		sessionBuilder.addProperties(getHibernateProperties());
		sessionBuilder.addAnnotatedClasses(ACMetrics.class);
		sessionBuilder.addAnnotatedClasses(Application.class);
		sessionBuilder.addAnnotatedClasses(Analyst.class);
		sessionBuilder.addAnnotatedClasses(Operation.class);
		return sessionBuilder.buildSessionFactory();
	}

	@Autowired
	@Bean(name = "transactionManager")
	public HibernateTransactionManager getTransactionManager(
			SessionFactory sessionFactory) {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager(
				sessionFactory);

		return transactionManager;
	}

	@Autowired
	@Bean(name = "acMetricsDAO")
	public ACMetricsDAO getACMetricsDAO(SessionFactory sessionFactory) {
		return new ACMetricsDAOImpl(sessionFactory);
	}

	@Autowired
	@Bean(name = "analystDAO")
	public AnalystDAO getAnalystDAO(SessionFactory sessionFactory) {
		return new AnalystDAOImpl(sessionFactory);
	}

	@Autowired
	@Bean(name = "applicationDAO")
	public ApplicationDAO getApplicationDAO(SessionFactory sessionFactory) {
		return new ApplicationDAOImpl(sessionFactory);
	}

	@Autowired
	@Bean(name = "operationDAO")
	public OperationDAO getOperationDAO(SessionFactory sessionFactory) {
		return new OperationDAOImpl(sessionFactory);
	}

	@Bean(name = "analystAuthoritiesPopulator")
	public AnalystAuthoritiesPopulator getAnalystAuthoritiesPopulator() {
		AnalystAuthoritiesPopulator analystAuthoritiesPopulator = new AnalystAuthoritiesPopulator();

		return analystAuthoritiesPopulator;
	}
}