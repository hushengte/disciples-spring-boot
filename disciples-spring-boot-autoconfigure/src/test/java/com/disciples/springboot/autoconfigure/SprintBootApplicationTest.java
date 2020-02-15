package com.disciples.springboot.autoconfigure;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.disciples.feed.json.HibernateProxyModule;
import com.disciples.feed.rest.RepositoryService;
import com.disciples.feed.web.RepositoryRestController;
import com.disciples.iam.config.IamWebSecurityConfigurerAdapter;

@SpringBootTest(classes = TestApp.class)
@RunWith(SpringRunner.class)
public class SprintBootApplicationTest {
    
    @Autowired(required = false)
    private HibernateProxyModule hibernateProxyModule;
    @Autowired(required = false)
    private RepositoryService repositoryService;
    @Autowired(required = false)
    private RepositoryRestController repositoryRestController;
    
    @Autowired(required = false)
    private IamWebSecurityConfigurerAdapter iamWebSecurityConfigurerAdapter;
    
    @Test
    public void beanExisits() {
        assertNotNull(hibernateProxyModule);
        assertNotNull(repositoryService);
        assertNotNull(repositoryRestController);
        assertNotNull(iamWebSecurityConfigurerAdapter);
    }
    
    @Test
    public void iamSecurityOrder() {
        assertEquals(-1, iamWebSecurityConfigurerAdapter.getOrder());
    }
    
}
