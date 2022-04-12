package com.example.application;

import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import java.util.Scanner;

/**
 * The entry point of the Spring Boot application.
 *
 * Use the @PWA annotation make the application installable on phones, tablets
 * and some desktop browsers.
 *
 */

/*
    This is mainly developed as a web-based application with Vaadin framework for the GUI.
    Hence, this program needs Apache Maven and Apache Tomcat to run on local machine's web browser (localhost:8080).
    Note that an internet connection is required inorder to start this application in GUI mode!
    To load the program, enter the numeric number prompted when you start the application.
 */
@SpringBootApplication
@Theme(value = "preinterviewselectionsystem")
@PWA(name = "PreInterview Selection System", shortName = "PreInterview Selection System", offlineResources = {
        "images/logo.png"})
@NpmPackage(value = "line-awesome", version = "1.3.0")
public class Application extends SpringBootServletInitializer implements AppShellConfigurator {


    //IMPORTANT function do not delete! (Can be a turning point)
    /*
    public static void main(String[] args) {
        System.out.println("This is running from SpringBoot application!");
        Run application with GUI in web browser
        SpringApplication.run(Application.class, args);
    }
     */

}
