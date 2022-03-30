package com.example.application;

import com.example.application.views.list.TerminalCode.HomeCLI;
import org.springframework.boot.SpringApplication;

import java.util.Scanner;

/*
    This is mainly developed as a web-based application with Vaadin framework for the GUI.
    Hence, this program needs Apache Maven and Apache Tomcat to run on local machine's web browser (localhost:8080)
    To load the program, enter the numeric number prompted when you start the application
 */

// 28/3/2022
/*
    Main class was added. Implemented function that allows user to specify whether to run in GUI or CLI
 */


public class Main {

    public static void main(String[] args) {
        System.out.println("This is from the main class!");
        //Run application with GUI in web browser
        // <-- Command Line Interface code starts here -->
        Scanner userInput = new Scanner(System.in);
        boolean validInput = false;

        while(validInput == false){

            System.out.println("\nWelcome to Pre-Interview Selection System." +
                    "\nTo run in terminal mode, enter 1. Else, enter 2 to enable GUI features (from main class)");
            String userOption = userInput.nextLine(); //reads user input

            if(userOption.equals("1")){
                //Instantiate Home class (candidate sign in page). Candidate sign in code is in the class constructor
                HomeCLI candidateSignIn = new HomeCLI();
                validInput = true;
            } else if (userOption.equals("2")){
                System.out.println("Starting GUI...");
                SpringApplication.run(Application.class, args);
            } else{
                System.out.println("Invalid input, please enter 1 or 2 only");
            }
        } //end of while loop

    }

}
