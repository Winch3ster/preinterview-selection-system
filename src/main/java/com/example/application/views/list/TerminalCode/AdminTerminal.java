package com.example.application.views.list.TerminalCode;

import java.util.Scanner;

public class AdminTerminal {
    private Scanner userInput = new Scanner(System.in);
    public AdminTerminal(){
        System.out.println("Welcome, which service do you want to access? Please enter the number associated to the service ONLY.");
        System.out.println("1 - Set Question \t. 2 - List Candidates information.");
        String choice = userInput.nextLine();

        if (choice.equals("1")){
            SetQuestionPageCLI runningSetQuestionPage = new SetQuestionPageCLI();
        } else if (choice.equals("2")){
            System.out.println("Displaying candidates list");
        } else {
            System.out.println("Invalid input, please enter 1 or 2 only.");
        }

    }

}
