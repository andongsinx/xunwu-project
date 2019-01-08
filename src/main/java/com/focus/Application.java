package com.focus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigurationExcludeFilter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.TypeExcludeFilter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

/**
 * 启动类
 */
@SpringBootConfiguration
@ComponentScan(
        excludeFilters = {@ComponentScan.Filter(
                type = FilterType.CUSTOM,
                classes = {TypeExcludeFilter.class}
        ), @ComponentScan.Filter(
                type = FilterType.CUSTOM,
                classes = {AutoConfigurationExcludeFilter.class}
        )}
)
@EnableAutoConfiguration(exclude = {
        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class
})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }

}
