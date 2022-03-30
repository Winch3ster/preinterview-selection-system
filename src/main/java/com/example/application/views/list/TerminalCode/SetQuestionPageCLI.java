package com.example.application.views.list.TerminalCode;


import com.example.application.views.list.HiringManagerViews.QuestionBlock;
import com.vaadin.flow.component.Component;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class SetQuestionPageCLI {
    Scanner userInput = new Scanner(System.in);
    private int questionCount;
    private int questionNumber = 1;
    private ArrayList<QuestionBLockCLI> questionList = new ArrayList<>();
    private JSONArray questionListJSON = new JSONArray();


    SetQuestionPageCLI(){
        boolean continueOperation = true;

        while( questionCount <= 3 && continueOperation){
            System.out.println("Please select question type, by entering the number:\n"
                    + "1 - Multiple Choice Question\t"
                    + "2 - True / False\t"
                    + "3 -  Code"
            );
            String questionType = userInput.nextLine();

            System.out.println("\n#######################################################################################");
            System.out.println(questionNumber + ") " + "Please enter the question statement: ");
            String questionText = userInput.nextLine();

            if ( questionType.equalsIgnoreCase("1")) {
                multipleChoiceQuestion(questionText);
            } else if ( questionType.equalsIgnoreCase("2")){
                trueFalseQuestion(questionText);
            } else if (questionType.equals("3")) {
                codingQuestion(questionText);
            } else {
                System.out.println("Invalid input, please enter 1, 2 or 3 only.");
            }

            if (questionCount >= 3 ){
                boolean validInput = false;
                while(validInput == false){
                    System.out.println("Do you want to continue adding question? (Yes / No)");
                    String userChoice = userInput.nextLine();

                    if (userChoice.equalsIgnoreCase("No")){
                        validInput = true;
                        continueOperation = false;
                        packQuestions();
                        HomeCLI backToHome = new HomeCLI();
                    } else if (userChoice.equalsIgnoreCase("Yes")){
                        validInput = true;
                    } else {
                        System.out.println("Invalid input, please enter Yes or No only.");
                    }
                }//end of while loop
            }
        }
    }// end of constructor
    //Question types --> "Multiple Choice", "True False", "Code"
    private void multipleChoiceQuestion(String questionStatement) {
        String questionType = "Multiple Choice";
        String questionText = questionStatement;
        System.out.print("option1: ");
        String optionOne = userInput.nextLine();

        System.out.print("Option 2: ");
        String optionTwo = userInput.nextLine();

        System.out.print("Option 3: ");
        String optionThree = userInput.nextLine();

        System.out.print("Option 4: ");
        String optionFour = userInput.nextLine();

        System.out.print("Please enter the answer (Check your spelling!):");
        String answer = userInput.nextLine();
        boolean answerGiven = false;
        while (answerGiven == false){
            if (answer.equals("")){
                System.out.println("Answer given cannot be null. Please enter the answer: ");
            } else {
                answerGiven = true;
            }

        }

        ArrayList<String> options  = new ArrayList<>();
        options.add(optionOne);
        options.add(optionTwo);
        options.add(optionThree);
        options.add(optionFour);
        System.out.println("#######################################################################################");

        //(String questionType, String questionStatement,String answer, ArrayList<String> options )
        QuestionBLockCLI questionblock = new QuestionBLockCLI(questionType, questionText, answer, options);
        questionList.add(questionblock);
        questionNumber++;
        questionCount++;
    }


    private void trueFalseQuestion(String questionStatement) {
        String questionType = "True False";
        String questionText = questionStatement;
        System.out.println("a) True \t b) False");
        System.out.println("Please enter the correct answer");
        System.out.println("(Enter True or False only): ");
        String answer = userInput.nextLine();
        boolean answerGiven = false;
        while (answerGiven == false){
            if (answer.equals("")){
                System.out.println("Answer given cannot be null. Please enter the answer: ");
            } else {
                answerGiven = true;
            }

        }

        ArrayList<String> options = new ArrayList<>();
        options.add("True");
        options.add("False");
        QuestionBLockCLI questionblock = new QuestionBLockCLI(questionType, questionText, answer, options);
        questionList.add(questionblock);
        System.out.println("#######################################################################################");
        questionNumber++;
        questionCount++;
    }

    private void codingQuestion(String questionStatement){
        String questionType = "Code";
        String questionText = questionStatement;
        System.out.print("Please enter the expected answer: ");
        String answer = userInput.nextLine();
        boolean answerGiven = false;
        while (answerGiven == false){
            if (answer.equals("")){
                System.out.println("Answer given cannot be null. Please enter the answer: ");
            } else {
                answerGiven = true;
            }
        }

        ArrayList<String> options = new ArrayList<>();
        options.add("");
        QuestionBLockCLI questionblock = new QuestionBLockCLI(questionType, questionText, answer,options);
        questionList.add(questionblock);
        System.out.println("#######################################################################################");
        questionNumber++;
        questionCount++;
    }

    private void packQuestions() {
        //Hash map to store info into JSON
        for(int z = 0; z < questionList.size(); z++){
            QuestionBLockCLI currentQuestionBlock = questionList.get(z);
            if (currentQuestionBlock instanceof QuestionBLockCLI){
                JSONObject questionDetails = new JSONObject();

                String questionType = ((QuestionBLockCLI) currentQuestionBlock).getQuestionType();
                //Get question from question block
                String questionText = ((QuestionBLockCLI) currentQuestionBlock).getQuestionStatement();

                //Get options from option block
                ArrayList questionOptions = ((QuestionBLockCLI) currentQuestionBlock).getOptions();
                //To be changed
                String answer = ((QuestionBLockCLI) currentQuestionBlock).getAnswer();

                questionDetails.put("questionText", questionText);
                questionDetails.put("questionType", questionType);
                questionDetails.put("answer", answer);
                questionDetails.put("options", questionOptions);
                //
                if (questionType.equals("Multiple Choice") || questionType.equals("True False")){
                    //Get options from option block
                    ArrayList<String> options = ((QuestionBLockCLI) currentQuestionBlock).getOptions();
                    questionDetails.put("option", options);
                }

                //inside this questionObject is another JSON object storing all the question information
                JSONObject questionObject = new JSONObject();
                questionObject.put("question", questionDetails);
                questionListJSON.add(questionObject);
            }
        }
        //Creating a JSON file
        try (FileWriter file = new FileWriter("QuestionListJSON.json")) {
            //We can write any JSONArray or JSONObject instance to the file
            file.write(questionListJSON.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
