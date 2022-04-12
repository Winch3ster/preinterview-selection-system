package com.example.application.views.list.TerminalCode;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

//Set question page CLI mode
public class SetQuestionPageCLI {
    private final Scanner userInput = new Scanner(System.in);
    private int questionCount;
    private int questionNumber = 1;
    private ArrayList<QuestionBLockCLI> questionList = new ArrayList<>();
    private JSONArray questionListJSON = new JSONArray();


    SetQuestionPageCLI(){
        boolean continueOperation = true;
        //Minimum 3 questions have to be set inorder to continue
        boolean enteredQuestionType = false;
        while(!enteredQuestionType){
            while( questionCount <= 3 && continueOperation){
                System.out.println("Please select question type, by entering the number:\n"
                        + "1 - Multiple Choice Question\t"
                        + "2 - True / False\t"
                        + "3 -  Code"
                );
                String questionType = userInput.nextLine();
                if (questionType.equals("1") || questionType.equals("2") || questionType.equals("3")){
                    enteredQuestionType = true;

                    boolean enteredQuestionStatement = false;
                    while (!enteredQuestionStatement){
                        System.out.println("\n#######################################################################################");
                        System.out.println(questionNumber + ") " + "Please enter the question statement: ");
                        String questionText = userInput.nextLine();
                        if (questionText.equalsIgnoreCase("")){
                            System.out.println("Error: Question statement cannot be null");
                        } else {
                            if ( questionType.equalsIgnoreCase("1")) {
                                multipleChoiceQuestion(questionText);
                            } else if ( questionType.equalsIgnoreCase("2")){
                                trueFalseQuestion(questionText);
                            } else if (questionType.equals("3")) {
                                codingQuestion(questionText);
                            } else {
                                System.out.println("Invalid input, please enter 1, 2 or 3 only.");
                            }
                            enteredQuestionStatement = true;
                        }
                    }
                } else {
                    System.out.println("Error: Please select at least one question type.");
                }
                if (questionCount >= 3 ){
                    boolean validInput = false;
                    while(!validInput){
                        System.out.println("Do you want to continue adding question? (Yes / No)");
                        String userChoice = userInput.nextLine();

                        if (userChoice.equalsIgnoreCase("No")){
                            validInput = true;
                            continueOperation = false;
                            packQuestions();
                            new InterviewDate(); // run set interview page
                        } else if (userChoice.equalsIgnoreCase("Yes")){
                            validInput = true;
                        } else {
                            System.out.println("Invalid input, please enter Yes or No only.");
                        }
                    }//end of while loop
                }
            }
        }
    }// end of constructor


    //Question types --> "Multiple Choice", "True False", "Code"
    private void multipleChoiceQuestion(String questionStatement) {
        String questionType = "Multiple Choice";

        //Prompt for option 1 to option 4
        System.out.print("option 1: ");
        String optionOne = userInput.nextLine();

        System.out.print("Option 2: ");
        String optionTwo = userInput.nextLine();

        System.out.print("Option 3: ");
        String optionThree = userInput.nextLine();

        System.out.print("Option 4: ");
        String optionFour = userInput.nextLine();

        System.out.print("Please enter the answer (Check your spelling!):");
        String answer = userInput.nextLine();

        //If answer is null, prompt again for an answer
        boolean answerGiven = false;
        while (!answerGiven){
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
        QuestionBLockCLI questionblock = new QuestionBLockCLI(questionType, questionStatement, answer, options);
        questionList.add(questionblock);
        questionNumber++;
        questionCount++;
    }

    //True false question
    private void trueFalseQuestion(String questionStatement) {
        String questionType = "True False";
        System.out.println("a) True \t b) False");
        System.out.println("Please enter the correct answer");

        //getting answer
        System.out.println("(Enter True or False only): ");
        String answer = userInput.nextLine();

        boolean answerGiven = false;
        while (!answerGiven){
            if (answer.equals("")){
                System.out.println("Answer given cannot be null. Please enter the answer: ");
            } else {
                answerGiven = true;
            }

        }

        ArrayList<String> options = new ArrayList<>();
        options.add("True");
        options.add("False");
        QuestionBLockCLI questionblock = new QuestionBLockCLI(questionType, questionStatement, answer, options);
        questionList.add(questionblock);
        System.out.println("#######################################################################################");
        questionNumber++;
        questionCount++;
    }

    //Coding question
    private void codingQuestion(String questionStatement){
        String questionType = "Code";
        System.out.print("Please enter the expected answer: ");
        String answer = userInput.nextLine();
        boolean answerGiven = false;
        while (!answerGiven){
            if (answer.equals("")){
                System.out.println("Answer given cannot be null. Please enter the answer: ");
            } else {
                answerGiven = true;
            }
        }

        ArrayList<String> options = new ArrayList<>();
        options.add("");
        QuestionBLockCLI questionblock = new QuestionBLockCLI(questionType, questionStatement, answer,options);
        questionList.add(questionblock);
        System.out.println("#######################################################################################");
        questionNumber++;
        questionCount++;
    }

    private void packQuestions() {
        //Loop through entire question list to extract the question objects
        for(int z = 0; z < questionList.size(); z++){
            QuestionBLockCLI currentQuestionBlock = questionList.get(z);
            if (currentQuestionBlock != null){
                JSONObject questionDetails = new JSONObject();
                String questionType =  currentQuestionBlock.getQuestionType();
                //Get question from question block
                String questionText = currentQuestionBlock.getQuestionStatement();

                //Get options from option block
                ArrayList<String> questionOptions = currentQuestionBlock.getOptions();
                //To be changed
                String answer = currentQuestionBlock.getAnswer();

                questionDetails.put("questionText", questionText);
                questionDetails.put("questionType", questionType);
                questionDetails.put("answer", answer);
                questionDetails.put("options", questionOptions);
                //
                if (questionType.equals("Multiple Choice") || questionType.equals("True False")){
                    //Get options from option block
                    ArrayList<String> options = currentQuestionBlock.getOptions();
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
