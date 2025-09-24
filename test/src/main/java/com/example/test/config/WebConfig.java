/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.test.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *
 * @author ADmiN
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/external-images/**")
                .addResourceLocations("file:///C:/Users/ADmiN/Documents/books/images/");
        registry.addResourceHandler("/pdfs/**")
                .addResourceLocations("file:///C:/Users/ADmiN/Documents/books/uploads/");
    }
      @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/404").setViewName("404");
    }
}

