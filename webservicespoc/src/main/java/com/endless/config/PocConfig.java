package com.endless.config;

import com.endless.service.PocService;
import com.endless.service.PocServiceImpl;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;
import org.apache.cxf.jaxws.spring.JaxWsWebServicePublisherBeanPostProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySources;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.support.ResourcePropertySource;

import java.io.File;
import java.io.IOException;

@Configuration
public class PocConfig {

    @Value("${pocservice.urlprefix:http://localhost:9090/}")
    private String urlPrefix;

    @Bean
    public PocService pocService() {
        return new PocServiceImpl();
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyResolver() throws IOException {
        PropertySourcesPlaceholderConfigurer pspc = new PropertySourcesPlaceholderConfigurer();
        pspc.setPropertySources(propertySources());
        return pspc;
    }

    @Bean
    public static PropertySources propertySources() throws IOException {
        MutablePropertySources mps = new MutablePropertySources();
        File propertyFile = new File(System.getProperty("poc.properties.location", "src/main/resources/config/poc.properties"));
        System.out.println("************ " + propertyFile.getAbsolutePath());
        FileSystemResource fsr = new FileSystemResource(propertyFile);
        mps.addLast(new ResourcePropertySource(fsr));

        return mps;
    }

    // This bean will be picked by the bean post processor
    // And this should be in Prototype scope - one instance per one end point
    @Bean(name = "prototypeServerFactory")
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public JaxWsServerFactoryBean jaxWsServerFactoryBean() {
        JaxWsServerFactoryBean serverFactory = new JaxWsServerFactoryBean();
        /*serverFactory.setServiceClass(PocService.class);
        serverFactory.setServiceBean(pocService());
        serverFactory.setAddress("http://localhost:9090/PocService");
        serverFactory.create();*/
        return serverFactory;
    }

    // Bean Post Processor to publish all endpoints (beans with @WebService annotation)
    @Bean
    public BeanPostProcessor wsBeanPostProcessor() throws NoSuchMethodException, ClassNotFoundException {
        JaxWsWebServicePublisherBeanPostProcessor beanPostProcessor = new JaxWsWebServicePublisherBeanPostProcessor();
        beanPostProcessor.setPrototypeServerFactoryBeanName("prototypeServerFactory");
        beanPostProcessor.setUrlPrefix(urlPrefix);
        return beanPostProcessor;
    }
}
