package com.disciples.springboot.autoconfigure.feed;

import java.util.Collections;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.repository.Repository;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.disciples.feed.config.RepositoryConfiguration;
import com.disciples.feed.json.HibernateProxyModule;
import com.disciples.feed.web.FacadeResponseBodyAdvice;
import com.disciples.feed.web.RepositoryRestController;

@Configuration(proxyBeanMethods = false)
@ConditionalOnClass({RepositoryConfiguration.class, Repository.class})
@Import({RepositoryConfiguration.class})
public class RepositoryAutoConfiguration {
	
	@Bean
	@ConditionalOnWebApplication
	@ConditionalOnMissingBean
	public RepositoryRestController repositoryRestController() {
		return new RepositoryRestController();
	}
	
	@Bean
	@ConditionalOnClass(name = {"org.hibernate.proxy.HibernateProxy"})
	@ConditionalOnMissingBean
	public HibernateProxyModule hibernateProxyModule() {
		return new HibernateProxyModule();
	}
	
	@Configuration(proxyBeanMethods = false)
	@ConditionalOnWebApplication
	@ConditionalOnProperty(prefix = "feed.response", name = "enabled", matchIfMissing = true)
	public static class MvcRegistrations implements WebMvcRegistrations {
		
		@Override
		public RequestMappingHandlerAdapter getRequestMappingHandlerAdapter() {
			RequestMappingHandlerAdapter handlerAdapter = new RequestMappingHandlerAdapter();
			handlerAdapter.setResponseBodyAdvice(Collections.<ResponseBodyAdvice<?>>singletonList(new FacadeResponseBodyAdvice()));
			return handlerAdapter;
		}

	}
	
}
