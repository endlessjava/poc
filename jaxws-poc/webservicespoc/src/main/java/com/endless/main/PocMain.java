package com.endless.main;

import org.apache.cxf.jaxws.JaxWsServerFactoryBean;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class PocMain {

    public static void main(String... args) {
        JaxWsServerFactoryBean serverFactory = new JaxWsServerFactoryBean();

        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext("com.endless.config");
        /*applicationContext.refresh();
        applicationContext.start();*/
    }
}
