package com.example.application.views.list.TerminalCode;

import java.util.Scanner;
//Home page (console mode)
public class HomeCLI {
    private Scanner userInput = new Scanner(System.in);

    public HomeCLI() {
        boolean validInput = false;

        while (!validInput) {
            System.out.println("\nWelcome to Pre-Interview Selection System.\nPlease select an sign-in option by entering 1 or 2 only:");
            System.out.println("1. Candidate");
            System.out.println("2. Hiring Manager");
            System.out.println("3. Quit");
            String userOption = userInput.nextLine(); //reads user input

            switch (userOption) {
                case "1" -> {
                    //Instantiate Home class (candidate sign in page). Candidate sign in code is in the class constructor
                    candidateSignIn();
                    validInput = true;
                }
                case "2" -> {
                    adminSignIn();
                    validInput = true;
                }
                case "3" -> {
                    validInput = true;
                    System.exit(0);
                }
                default -> System.out.println("Invalid input, please enter 1, 2 or 3 only");
            }
        }
    }

    //Sign into the system as a candidate
    private void candidateSignIn(){
        Scanner candidateInput = new Scanner(System.in);
        System.out.println("Please enter your name and email address to continue.");

        //Prompt candidate's name
        System.out.println("Name: ");
        String candidateName = candidateInput.nextLine();

        //Prompt candidate's email address
        System.out.println("Email address: ");
        String candidateEmail = candidateInput.nextLine();

        //Run exam page in terminal
        System.out.println("Registered. Redirecting you to exam page. Good luck!");
        new ExamPageCLI(candidateName, candidateEmail);
        }

        //Sign in as an admin (Hiring manager)
    private void adminSignIn() {
        boolean signedIn = false;
        System.out.println("Signing in as admin...");
        while (!signedIn){
            System.out.print("Enter username: ");
            String userName = userInput.nextLine();

            System.out.print("Enter your password: ");
            String password = userInput.nextLine();

            //Validate username and password
            if (userName.equals("user") && password.equals("user")){
                new AdminTerminal();
                signedIn = true;
            } else {
                System.out.println("Username and password incorrect. Please try again.");
            }

        }
    }


}





