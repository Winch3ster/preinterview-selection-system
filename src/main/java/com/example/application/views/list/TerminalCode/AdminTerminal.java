package com.example.application.views.list.TerminalCode;

import java.util.Scanner;

public class AdminTerminal {
    public AdminTerminal(){
        Scanner userInput = new Scanner(System.in);

        // Admin menu
        System.out.println("Welcome, which service do you want to access? Please enter the number associated to the service ONLY.");
        System.out.println("1 - Set Questions. \t 2 - List Candidates information. \t 3 - Update interview date. \t 4 - Sign out.");
        String choice = userInput.nextLine();

        boolean validInput = false;
        //Prompt for new input when user entered an invalid option
        while(!validInput) {
            if (choice.equals("1")) {

                new SetQuestionPageCLI(); //Run set question class (page)

            } else if (choice.equals("2")) {

                new ListViewCLI();

            } else if (choice.equals("3")){

                new InterviewDate(); //Run set interview date class

            }else if (choice.equals("4")){

                //Return to home menu
                System.out.println("Signing out...");
                new HomeCLI();

            } else {
                System.out.println("Invalid input, please enter 1, 2, 3 or 4 only.");
            }
        }

    }

}
