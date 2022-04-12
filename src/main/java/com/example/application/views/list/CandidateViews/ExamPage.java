package com.example.application.views.list.CandidateViews;

import com.example.application.data.Repository.ContactRepository;
import com.example.application.data.entity.Contact;
import com.example.application.views.list.HiringManagerViews.InterviewDatePage;
import com.example.application.views.list.Home;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
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
import java.util.Iterator;
import java.util.List;


@PageTitle("Exam Page")
@Route("examPage")
public class ExamPage extends VerticalLayout {

    //Candidates variable
    private int candidateScore;
    private String interviewDate;

    //Getting the name and email from Home.class (From the login fields)
    private String candidateName = Home.candidateName;
    private String candidateEmail = Home.candidateEmail;

    //GUI components
    private VerticalLayout evaluateButtonLayout = new VerticalLayout();
    private JSONParser jsonParser = new JSONParser();
    private Button evaluateButton = new Button();
    private Button dialogCloseButton = new Button();
    private Dialog results = new Dialog();
    private VerticalLayout header = new VerticalLayout();
    private VerticalLayout mainContent = new VerticalLayout(); //Stores question blocks
    private VerticalLayout dialogLayoutHeader = new VerticalLayout();

    //Data from the ContactRepository class
    ArrayList<Contact> contactExamPage = ContactRepository.contacts;


    public ExamPage(){

        dialogLayoutHeader.addClassName("dialog-header");
        //Read JSON file
        try (FileReader reader = new FileReader("QuestionListJSON.json"))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);

            JSONArray questionList = (JSONArray) obj;

            //instantiate question block according to JSON object
            questionList.forEach( question -> parseQuestionObject( (JSONObject) question ) );
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //Configuring web page
        addClassName("exam-page");
        setSizeFull();
        configureHeader();
        configureEvaluateButtonLayout();
        configureEvaluateButton();
        //Things to add in the page
        add( header, mainContent, evaluateButtonLayout);

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
            checkAnswers(); //Evaluate the answers

            //Calculate the user score
            double score = ((double) candidateScore/ (double) mainContent.getComponentCount()) * 100;

            //Update the contact list in the ContactRepository class
            contactExamPage.add(new Contact(candidateName, candidateEmail, (int) Math.round(score)));
            ContactRepository.updateList(contactExamPage);


            getInterviewDate();
            configureDialogCloseButton();
            VerticalLayout dialogLayoutContent = new VerticalLayout();

            //If user scored above 60, output the interview date. Else, no
            if (score >= 60){
                dialogLayoutHeader.add(   new H2("Results"));
                dialogLayoutContent.add(new H3("Congratulation, you have passed the exam with the score of: " + Math.round(score)),
                                        new H3("The interview date will be on " + interviewDate),
                                        new H3("Hope to see you soon!"));
            } else {
                dialogLayoutHeader.add(   new H2("Results"),
                                    new H3("Thank you for taking the exam. Your score is: " + Math.round(score)),
                                    new H3("Your application will be considered.")

                );
            }
            results.add(dialogLayoutHeader, dialogLayoutContent, dialogCloseButton);
            //Display the dialog in the web page
            results.open();

        });
    }

    private void configureDialogCloseButton() {
        dialogCloseButton.setText("close");
        dialogCloseButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        dialogCloseButton.addClickListener(clicked -> {
            results.close();
            UI.getCurrent().navigate("");
        });
    }

    private void checkAnswers() {
        for (int i =0; i < mainContent.getComponentCount(); i++){
            Component currentCom = mainContent.getComponentAt(i);//get each object
            //Check if the object is an instance of QuestionBlocks class
            if(currentCom instanceof QuestionBlockCandidate){
                String givenAnswer = (((QuestionBlockCandidate) currentCom).getAnswer()).toLowerCase();
                String userAns = (((QuestionBlockCandidate) currentCom).getUserAnswer()).toLowerCase();
                if (userAns.contains(givenAnswer)){
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

    //Get interview date from interviewDate.json
    private void getInterviewDate(){
        try (FileReader reader = new FileReader("interviewDate.json"))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);
            JSONObject interview = (JSONObject) obj;
            JSONObject interviewDateObject = (JSONObject) interview.get("interviewDate");

            //set the interview date variable (from this class) with the date from JSON file
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
