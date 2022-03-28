package com.example.application.views.list.CandidateViews;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
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

    private int candidateScore;
    private HorizontalLayout evaluateButtonLayout = new HorizontalLayout();
    private JSONParser jsonParser = new JSONParser();
    private Button evaluateButton = new Button();

    // <-- Test data -->
    ArrayList<HashMap<String, ArrayList<String>>> questionAndAnswerToBeLoadedList = new ArrayList<>();

    private HashMap<String, ArrayList<String>> questionAndAnswerToBeLoaded1 = new HashMap<>();
    ArrayList<String> optionTest1 = new ArrayList<>();

    private HashMap<String, ArrayList<String>> questionAndAnswerToBeLoaded2 = new HashMap<>();
    ArrayList<String> optionTest2 = new ArrayList<>();
    // <-- Test data -->


    private VerticalLayout header = new VerticalLayout();
    private VerticalLayout mainContent = new VerticalLayout(); //Stores question blocks

    public ExamPage(){

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
        //Read JSON file
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

        // <-- GUI for exam page starts here -->
        //Configuring web page
        addClassName("exam-page");
        setSizeFull();
        configureHeader();
        configureEvaluateButtonLayout();
        configureEvaluateButton();
        //Things to add in the page
        add( header, mainContent, evaluateButton);

        // <-- GUI for exam page ends here -->


        // <-- Command Line Interface code starts here -->


        // <-- Command Line Interface code ends here -->




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
            candidateScore =0;
            checkAnswers();
            //To be changed
            Notification.show("Your Score is " + candidateScore);
        });
    }

    private void checkAnswers() {
        for (int i =0; i < mainContent.getComponentCount(); i++){
            Component currentCom = mainContent.getComponentAt(i);//get each object
            //Check if the object is an instance of QuestionBlocks class
            if(currentCom instanceof QuestionBlockCandidate){
                String legitAnswer = ((QuestionBlockCandidate) currentCom).getAnswer();
                String userAns = ((QuestionBlockCandidate) currentCom).getUserAnswer();
                //System.out.println(legitAnswer);
                //System.out.println(userAns);
                if (userAns.equals(legitAnswer)){
                    //System.out.println("this is running from check answer equality");
                    candidateScore += 1; //increase candidateScore by 1 when candidate's answer match HM answer
                }
            }
        }
    }

    private void configureHeader() {
        header.addClassName("examPage-header"); //CSS class name
        header.setWidthFull();
        H1 examPageTitle = new H1("Pre-Interview Selection System | Exam Page");
        examPageTitle.addClassNames("text-l", "m-m"); //CSS style
        header.add(examPageTitle);
    }

    //Instantiating questions from JSON file to the exam page
    private void parseQuestionObject(JSONObject question) {
        //Get JSON object from JSONArray
        JSONObject questionObject = (JSONObject) question.get("question");

        //Extracting question information
        String questionType = (String) questionObject.get("questionType");
        String questionText = (String) questionObject.get("questionText");
        String answer = (String) questionObject.get("answer");

        if (questionType.equals("Multiple Choice") || questionType.equals("True False")){

            //extract options from JSON inner option array
            ArrayList<String> temp = new ArrayList<>();
            JSONArray jsonArray = (JSONArray) questionObject.get("option");
            Iterator<String> iterator = jsonArray.iterator();
            while(iterator.hasNext()) {
                temp.add(iterator.next());
            }
            //Instantiate question block
            QuestionBlockChoicesCandidate questionBlockChoices = new QuestionBlockChoicesCandidate(questionText, answer, temp);
            mainContent.add(questionBlockChoices);
        } else {
            QuestionBlockCodeCandidate questionBlockCode = new QuestionBlockCodeCandidate(questionText, answer);
            mainContent.add(questionBlockCode);
            //System.out.println("This is running");
        }
    }




}
