package com.example.application.views.list.TerminalCode;


import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

//Set interview date
public class InterviewDate {

    private String interviewDate;

    public InterviewDate(){
        Scanner input = new Scanner(System.in);

        //Re-run this class (page), if the hiring manager wants to change the interview date

        boolean unconfirmedInterviewDate = true;
        while(unconfirmedInterviewDate){
            System.out.println("Date format --> day / month / year ");
            System.out.println("Please insert the available interview date: ");
            String date = input.nextLine();
            if (!date.equals(null)){
                System.out.println("The interview date will be on: " + date);
                System.out.println("Do you want to continue? (Yes or No)");

                String userInput = input.nextLine();
                if (userInput.equalsIgnoreCase("Yes")) {
                    interviewDate = date;
                    unconfirmedInterviewDate = false;
                    saveInterviewDateJSON();
                    new HomeCLI();
                } else{
                    System.out.println("Reselect another date");
                }
            }
        }
    }//end of constructor

    //Write the interview date to interViewDate.json file
    private void saveInterviewDateJSON() {
        System.out.println("This is running from saveInterviewDateJSON");
        JSONObject interviewDateJSON = new JSONObject();
        interviewDateJSON.put("date" , interviewDate);
        JSONObject json = new JSONObject();
        json.put("interviewDate", interviewDateJSON);
        try (FileWriter file = new FileWriter("interviewDate.json")) {
            //We can write any JSONArray or JSONObject instance to the file
            file.write(json.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public String getInterviewDate(){
        return interviewDate;
    }

}
