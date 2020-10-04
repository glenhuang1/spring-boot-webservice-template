package com.glenhuang.template.springboot.ws.config;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class ExternalPropConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer properties() {
        PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer = new PropertySourcesPlaceholderConfigurer();

        //Single yml file
        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
        yaml.setResources(new ClassPathResource("external-app.yml"));
        propertySourcesPlaceholderConfigurer.setProperties(yaml.getObject());

        //Multiple yml files
//        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
//        yaml.setResources(new ClassPathResource("external-app.yml"));
//        YamlPropertiesFactoryBean yaml2 = new YamlPropertiesFactoryBean();
//        yaml2.setResources(new ClassPathResource("external-app-2.yml"));
//        propertySourcesPlaceholderConfigurer.setPropertiesArray(yaml.getObject(), yaml2.getObject());

        //Multiple yml & properties files
//        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
//        yaml.setResources(new ClassPathResource("external-app.yml"));
//        YamlPropertiesFactoryBean yaml2 = new YamlPropertiesFactoryBean();
//        yaml2.setResources(new ClassPathResource("external-app-2.yml"));
//        propertySourcesPlaceholderConfigurer.setPropertiesArray(yaml.getObject(), yaml2.getObject());
//        propertySourcesPlaceholderConfigurer.setLocations(new ClassPathResource("external-app.properties"));

        return propertySourcesPlaceholderConfigurer;
    }
}
