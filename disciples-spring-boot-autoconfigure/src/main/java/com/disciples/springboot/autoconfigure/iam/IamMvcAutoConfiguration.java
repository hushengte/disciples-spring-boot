package com.disciples.springboot.autoconfigure.iam;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import com.disciples.iam.config.IamWebSecurityConfigurerAdapter;
import com.disciples.iam.config.ServiceConfiguration;
import com.disciples.iam.web.UserManageController;

@Configuration
@ConditionalOnWebApplication
@AutoConfigureAfter({FreeMarkerAutoConfiguration.class})
@Import(ServiceConfiguration.class)
@ComponentScan(basePackageClasses = {UserManageController.class})
public class IamMvcAutoConfiguration {
    
    public static final String SQL_PATH_SCHEMA = "/database/mysql/core.sql";
    public static final String SQL_PATH_DATA = "/database/mysql/data.sql";
    
    @Autowired
    private DataSource dataSource;
    
    @PostConstruct
    public void init() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator(new ClassPathResource(SQL_PATH_SCHEMA));
        populator.execute(dataSource);
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        Long userCount = jdbcTemplate.queryForObject("select count(id) from iam_user", Long.class);
        if (userCount.intValue() == 0) {
            populator.setScripts(new ClassPathResource(SQL_PATH_DATA));
            populator.execute(dataSource);
        }
    }
	
	@Bean
	@ConditionalOnMissingBean({IamWebSecurityConfigurerAdapter.class})
	public IamWebSecurityConfigurerAdapter iamWebSecurityConfigurerAdapter() {
		return new IamWebSecurityConfigurerAdapter();
	}
	
}
