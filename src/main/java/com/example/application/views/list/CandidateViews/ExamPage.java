package com.example.application.views.list.CandidateViews;

import com.example.application.views.list.HiringManagerViews.QuestionBlock;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;


@PageTitle("Exam Page")
@Route("examPage")
public class ExamPage extends VerticalLayout {

    int score =0;

    HorizontalLayout evaluateButtonLayout = new HorizontalLayout();
    JSONParser jsonParser = new JSONParser();
    Button evaluateButton = new Button();

    // <-- Test data -->
    ArrayList<HashMap<String, ArrayList<String>>> questionAndAnswerToBeLoadedList = new ArrayList<>();

    private HashMap<String, ArrayList<String>> questionAndAnswerToBeLoaded1 = new HashMap<>();
    ArrayList<String> optionTest1 = new ArrayList<>();

    private HashMap<String, ArrayList<String>> questionAndAnswerToBeLoaded2 = new HashMap<>();
    ArrayList<String> optionTest2 = new ArrayList<>();
    // <-- Test data -->




    private VerticalLayout header = new VerticalLayout();
    private VerticalLayout mainContent = new VerticalLayout(); //Stores question blocks
    ExamPage(){

        // <-- Test data -->
        optionTest1.add("Legend");
        optionTest1.add("Gay");
        optionTest1.add("Jesus");
        optionTest1.add("I miss him");
        questionAndAnswerToBeLoaded1.put("What do you think of papa Franku?" , optionTest1);

        optionTest2.add("True");
        optionTest2.add("False");
        questionAndAnswerToBeLoaded1.put("You are gay." , optionTest2);

        questionAndAnswerToBeLoadedList.add(questionAndAnswerToBeLoaded1);
        questionAndAnswerToBeLoadedList.add(questionAndAnswerToBeLoaded2);
        // <-- Test data end-->

        addClassName("exam-page");
        setSizeFull();
        configureHeader();


        configureEvaluateButtonLayout();
        configureEvaluateButton();

        //generateQuestionsExamPage();

        try (FileReader reader = new FileReader("QuestionListJSON.json"))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);

            JSONArray questionList = (JSONArray) obj;
            System.out.println(questionList);

            //instantiate question block according to JSON object
            questionList.forEach( question -> parseQuestionObject( (JSONObject) question ) );


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }


        //Things to add in the page
        add(
                header,
                mainContent,
                evaluateButton
        );


    } //end of constructor

    private void configureEvaluateButtonLayout() {

        evaluateButtonLayout.setWidthFull();
        evaluateButtonLayout.setAlignItems(Alignment.CENTER);
        evaluateButtonLayout.add(evaluateButton);

    }

    private void configureEvaluateButton() {
        evaluateButton.setText("Evaluate");
        evaluateButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        evaluateButton.addClickListener(event -> {
            score =0;
            checkAnswers();

            Notification.show("Your Score is " + score);
        });

    }


    // To be completed!!!!!!!!!!!!!!!!!!!
    private void checkAnswers() {
        for (int i =0; i < mainContent.getComponentCount(); i++){
            Component currentCom = mainContent.getComponentAt(i);//get each object
            //Check if the object is an instance of QuestionBlocks class
            if(currentCom instanceof QuestionBlockCandidate){
                String legitAnswer = ((QuestionBlockCandidate) currentCom).getAnswer();
                String userAns = ((QuestionBlockCandidate) currentCom).getUserAnswer();
                System.out.println(legitAnswer);
                System.out.println(userAns);

                if (userAns.equals(legitAnswer)){
                    System.out.println("this is running from check answer equality");
                    score += 1;
                }
            }
        }

    }


    private void configureHeader() {
        header.addClassName("examPage-header");
        header.setWidthFull();
        H1 examPageTitle = new H1("Pre-Interview Selection System | Exam Page");
        examPageTitle.addClassNames("text-l", "m-m"); //CSS style
        header.add(examPageTitle);
    }




    private void parseQuestionObject(JSONObject question) {

        JSONObject questionObject = (JSONObject) question.get("question");

        VerticalLayout questionBlock = new VerticalLayout();
        Label questionField = new Label();
        RadioButtonGroup<String> options = new RadioButtonGroup<>();


        // Mapping the question to exam page
        String questionType = (String) questionObject.get("questionType");
        //System.out.println(questionType);

        String questionText = (String) questionObject.get("questionText");
        String answer = (String) questionObject.get("answer");

        if (questionType.equals("Multiple Choice") || questionType.equals("True False")){
            //System.out.println("This inner Multiple choice statement is running");
            //extract options from JSON inner option array
            ArrayList<String> temp = new ArrayList<>();
            JSONArray jsonArray = (JSONArray) questionObject.get("option");
            Iterator<String> iterator = jsonArray.iterator();
            while(iterator.hasNext()) {
                temp.add(iterator.next());
            }
            QuestionBlockChoicesCandidate questionBlockChoices = new QuestionBlockChoicesCandidate(questionText, answer, temp);
            mainContent.add(questionBlockChoices);
        } else {
            QuestionBlockCodeCandidate questionBlockCode = new QuestionBlockCodeCandidate(questionText, answer);
            mainContent.add(questionBlockCode);
            //System.out.println("This is running");
        }



        /*
        questionField.setText(questionText);
        options.setItems(temp);
        questionBlock.add(questionField, options);
        mainContent.add(questionBlock);


        System.out.println(questionText);
        System.out.println(answer);
        System.out.println(questionObject.get("option"));

         */
    }




}
