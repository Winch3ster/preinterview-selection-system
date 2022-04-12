package com.example.application.views.list.TerminalCode;

import com.example.application.data.Repository.ContactRepository;
import com.example.application.data.entity.Contact;

import java.util.ArrayList;
import java.util.Scanner;

//List all the candidates' information
public class ListViewCLI {
    ArrayList<Contact> contactExamPage = ContactRepository.contacts;

    public ListViewCLI(){
        System.out.println("###############     Candidates list     ################");
        System.out.println("Name/ \t\t\t Email /\t\t\t\t Score /\t\t Test Taken");
        System.out.println("");

        //Loop through the arrayList containing the candidates' information and print them to the console
        for (int i =0 ; i < contactExamPage.size(); i++){
            Object currentObj = contactExamPage.get(i);
            if (currentObj instanceof Contact){
                Contact current = (Contact) currentObj;
                String name = current.getCandidateName();
                String email = current.getEmail();
                int score = current.getCandidateScore();
                boolean testTaken = current.getTestStatus();
                System.out.println(name + " \t " + email + " \t " + score + " \t\t " + testTaken);
            }
        }
        System.out.println("Enter Y to quit.");
        Scanner input = new Scanner(System.in);

        boolean validInput = false;
        while(!validInput){
            String choice = input.nextLine();
            if (choice.equalsIgnoreCase("Y")){
                validInput =true;
                new AdminTerminal();
            } else {
                System.out.println("Invalid input... enter Y to quit");
            }
        }


    }

}
