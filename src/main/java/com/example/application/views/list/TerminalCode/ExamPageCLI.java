package com.example.application.views.list.TerminalCode;

import com.example.application.data.Repository.ContactRepository;
import com.example.application.data.entity.Contact;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.lang.Object;

public class ExamPageCLI {
    int userScore;
    int totalQuestionCount;
    private JSONParser jsonParser = new JSONParser();
    private Scanner candidateAnswerInput = new Scanner(System.in);
    private String interviewDate;
    private String candidateName;
    private String candidateEmail;

    //Get candidate's name and email as a parameter from the HomeCLI class
    public ExamPageCLI(String name, String email){
        //set parameter to variable
        this.candidateName = name;
        this.candidateEmail = email;

        //Get Contact objects (candidates' information) from contactRepository class
        ArrayList<Contact> contactExamPage = ContactRepository.contacts;


        getInterviewDateJSON();

        System.out.println("\n\n###############   Instruction   ###############");
        System.out.println("You are required to enter you answer ONLY without a), b), c) or d)");
        System.out.println("Example: a) True     b) False ");
        System.out.println("Your entered answer should be --> True \n\n");

        //Read question list and print them to the console
        try (FileReader reader = new FileReader("QuestionListJSON.json"))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);

            JSONArray questionList = (JSONArray) obj;
            totalQuestionCount = questionList.size();
            //instantiate question block according to JSON object
            questionList.forEach( question -> parseQuestionObject( (JSONObject) question ) );
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        System.out.println("\n\nExam ends\n");

        //Calculate user score
        double score = (((double) userScore/ (double)totalQuestionCount))*100;
        System.out.println("Your score is: " + Math.round(score) +"%");

        //If the candidate scored higher than 60, display the interview date. Else, no
        if (score >= 60 ){
            System.out.println("Well done, the interview date is: " +  interviewDate);
        } else {
            System.out.println("Thank you for taking the exam. Your application will be considered.\n");
        }

        //Add the candidate's information such as name, email, and score to the ContactRepository class
        contactExamPage.add(new Contact(candidateName, candidateEmail, (int) Math.round(score)));
        ContactRepository.updateList(contactExamPage);


        new HomeCLI();
    }



    private void parseDate(JSONObject date){
        interviewDate = (String) date.get("date");
    }

    private void parseQuestionObject(JSONObject question) {
        //Get JSON object from JSONArray
        JSONObject questionObject = (JSONObject) question.get("question");

        //Extracting question information
        String questionType = (String) questionObject.get("questionType");
        String questionText = (String) questionObject.get("questionText");
        String answer = (String) questionObject.get("answer");

        //Instantiating multiple choice question in the terminal
        if (questionType.equals("Multiple Choice")){
            //extract options from JSON inner option array
            ArrayList<String> temp = new ArrayList<>();
            JSONArray jsonArray = (JSONArray) questionObject.get("option");
            Iterator<String> iterator = jsonArray.iterator();
            while(iterator.hasNext()) {
                temp.add(iterator.next());
            }
            //Instantiate question block
            System.out.println(questionText);
            System.out.println( "a) " + temp.get(0) + " " +
                                "b) " + temp.get(1) + " " +
                                "c) " + temp.get(2) + " " +
                                "d) " +  temp.get(3)
                    );
            String candidateAnswer = candidateAnswerInput.nextLine();
            checkAnswer(candidateAnswer, answer);

            //Instantiating true false question in the terminal
        } else if (questionType.equals("True False")){
            ArrayList<String> temp = new ArrayList<>();
            JSONArray jsonArray = (JSONArray) questionObject.get("option");

            //WARNING TEST FIRST
            //Used to be while loop
            temp.addAll(jsonArray);
            System.out.println(questionText);
            System.out.println( "a) " + temp.get(0) + " " +
                                "b) " + temp.get(1)
                );
            String candidateAnswer = candidateAnswerInput.nextLine();
            checkAnswer(candidateAnswer, answer);
        } else {
            //Instantiate coding question
            System.out.println(questionText);
            String candidateAnswer = candidateAnswerInput.nextLine();
            checkAnswer(candidateAnswer, answer);
        }
    }

    private void checkAnswer(String candidateAnswer, String answer) {
        if ((candidateAnswer.toLowerCase()).contains((answer.toLowerCase()))){
            userScore++;
        } else {
            userScore = userScore;
        }
    }
    private void getInterviewDateJSON() {
        //Read interviewDate.json file
        try (FileReader reader = new FileReader("interviewDate.json"))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);
            JSONObject interview = (JSONObject) obj;
            JSONObject interviewDateObject = (JSONObject) interview.get("interviewDate");
            interviewDate = (String) interviewDateObject.get("date");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
