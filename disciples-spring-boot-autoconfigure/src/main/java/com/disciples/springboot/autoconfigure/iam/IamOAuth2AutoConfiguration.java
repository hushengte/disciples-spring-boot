package com.disciples.springboot.autoconfigure.iam;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.disciples.iam.oauth2.config.AuthorizationServerConfiguration;
import com.disciples.iam.oauth2.config.OAuth2AuthorizationSecurityConfiguration;
import com.disciples.iam.oauth2.config.ResourceServerConfiguration;

@Configuration
@ConditionalOnClass({OAuth2AccessToken.class, WebMvcConfigurerAdapter.class})
@AutoConfigureBefore(WebMvcAutoConfiguration.class)
@Import({AuthorizationServerConfiguration.class, ResourceServerConfiguration.class, 
    OAuth2AuthorizationSecurityConfiguration.class})
public class IamOAuth2AutoConfiguration {
	
    public static final String SQL_PATH_OAUTH2 = "/database/mysql/oauth2.sql";
    
	@Autowired
	private DataSource dataSource;
	
	@PostConstruct
	public void init() {
		new ResourceDatabasePopulator(new ClassPathResource(SQL_PATH_OAUTH2)).execute(dataSource);
	}
	
}
