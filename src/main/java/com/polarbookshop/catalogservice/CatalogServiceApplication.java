package com.polarbookshop.catalogservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class CatalogServiceApplication {
    private static ApplicationContext applicationContext;
    public static void main(String[] args) {
        applicationContext = SpringApplication.run(CatalogServiceApplication.class, args);
        displayAllBeans();
    }

    public static void displayAllBeans() {
        String[] allBeanNames = applicationContext.getBeanDefinitionNames();
        for(String beanName : allBeanNames) {
            System.out.println(beanName);
        }
    }

}
