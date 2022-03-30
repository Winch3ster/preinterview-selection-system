package com.example.application.views.list.TerminalCode;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.lang.Object;

public class ExamPageCLI {
    int userScore;
    int totalQuestionCount;
    private JSONParser jsonParser = new JSONParser();
    Scanner candidateAnswerInput = new Scanner(System.in);
    public ExamPageCLI(){
        //System.out.println("This is running from exam page CLI");
        System.out.println("\n\n###############   Instruction   ###############");
        System.out.println("You are required to enter you answer ONLY without a), b), c) or d)");
        System.out.println("Example: a) True     b) False ");
        System.out.println("Your entered answer should be --> True \n\n");

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
        System.out.println("Your score is: " + userScore + " out of " + totalQuestionCount);
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
            Iterator<String> iterator = jsonArray.iterator();
            while(iterator.hasNext()) {
                temp.add(iterator.next());
            }
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
        if (candidateAnswer.equalsIgnoreCase(answer)){
            userScore++;
        }
    }
}
