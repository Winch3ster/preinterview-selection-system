package com.example.application.views.list.HiringManagerViews;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;


@PageTitle("Set Question")
@Route(value= "SetQuestionPage", layout = MainApplicationLayout.class)//Use the MainLayout class as its layout
public class SetQuestionPage extends VerticalLayout {

    // <-- Test data (to  be deleted) -->
    private VerticalLayout questionList = new VerticalLayout(); //Vertical layout to store the instantiated question blocks
    public HashMap<String, ArrayList<String>> questionsAndAnswer = new HashMap<>();
    // <-- Test data (to  be deleted) -->


    private VerticalLayout nextPageButtonLayout =new VerticalLayout();
    private VerticalLayout questionPageLayout = new VerticalLayout();

    JSONArray QuestionListJSON = new JSONArray();

    SetQuestionPage(){

        setSizeFull();

        configureNextPageLayout();
        configureQuestionPageLayout();

        add(
                nextPageButtonLayout,
                questionPageLayout
        );

    }

    private void configureQuestionPageLayout(){
        questionPageLayout.setAlignItems(Alignment.CENTER);
        questionPageLayout.add(
                saveQuestion(),
                questionList,
                buttonPanel());
    }

    //A panel to store all the button in horizontal alignment
    private Component buttonPanel(){
        HorizontalLayout buttonPanel = new HorizontalLayout();
        buttonPanel.setAlignItems(Alignment.BASELINE);
        buttonPanel.add(addQuestionButton(), removeAllBlocks());

        return buttonPanel;

    }

    //Configure "add question block" button
    public Button addQuestionButton(){
        Button addQuestionButton = new Button("ADD QUESTION");
        addQuestionButton.setVisible(true);

        //Add question button has a primary theme
        addQuestionButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        addQuestionButton.addClickListener(click ->
                //Generate question block
                addQuestionBlock()
        );
        return addQuestionButton;
    }

    //Generate question block
    private void addQuestionBlock() {
        QuestionBlock questionBlocks = new QuestionBlock();
        questionList.add(questionBlocks);
    }

    //Remove all deleted question blocks
    public Button removeAllBlocks(){
        Button remove = new Button("REMOVE DELETED");
        remove.addClickListener(click -> {
            for (int i = questionList.getComponentCount()-1; i >= 0; i--){
                Component currentCom = questionList.getComponentAt(i);//get each object
                //Check if the object is an instance of QuestionBlocks class
                if(currentCom instanceof QuestionBlock){
                    if(((QuestionBlock)currentCom).getState()){
                        questionList.remove(currentCom); //Remove the question block from the list
                    }
                }
            }
        });

        //return removeButton
        return remove;
    }
    private Button configureNextPage(){
        Button nextPage =new Button("NEXT");
        nextPage.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        nextPage.addClickListener(event -> UI.getCurrent().navigate("interviewDatePage"));

        return nextPage;
    }

    private void configureNextPageLayout(){
        nextPageButtonLayout.setAlignItems(Alignment.END);
        nextPageButtonLayout.add(configureNextPage());

    }

    //Save questions
    public Button saveQuestion(){
        Button saveQuestionButton = new Button("SAVE QUESTION");
        saveQuestionButton.addClickListener(onclick -> packQuestionsJSON());

        return saveQuestionButton;
    }

    //Pack questions block into JSON format
    private void packQuestions() {
        //Hash map to store info into JSON
        for(int z = 0; z < questionList.getComponentCount(); z++){
            Component currentQuestionBlock = questionList.getComponentAt(z);
            if (currentQuestionBlock instanceof QuestionBlock){

                //Get question from question block
                String questionText = ((QuestionBlock) currentQuestionBlock).getQuestionText();

                //Get options from option block
                ArrayList<String> questionOptions = ((QuestionBlock) currentQuestionBlock).getQuestionOptions();

                //To be changed
                String answer = ((QuestionBlock) currentQuestionBlock).getAnswer();
                //
                questionsAndAnswer.put(questionText, questionOptions); //Stores question and options into hashMap

            }
        }
        System.out.println(questionsAndAnswer);
    }

    public HashMap<String, ArrayList<String>> getQuestionsAndAnswer(){
        return  questionsAndAnswer;
    }


    private void packQuestionsJSON() {

        //Hash map to store info into JSON
        for(int z = 0; z < questionList.getComponentCount(); z++){

            Component currentQuestionBlock = questionList.getComponentAt(z);
            if (currentQuestionBlock instanceof QuestionBlock){

                JSONObject questionDetails = new JSONObject();
                //Get question from question block
                String questionText = ((QuestionBlock) currentQuestionBlock).getQuestionText();


                //To be changed
                String answer = ((QuestionBlock) currentQuestionBlock).getAnswer();
                //

                String questionType = ((QuestionBlock) currentQuestionBlock).getQuestionType();

                questionDetails.put("questionText", questionText);
                questionDetails.put("questionType", questionType);
                questionDetails.put("answer", answer);
                if (questionType.equals("Multiple Choice") || questionType.equals("True False")){
                    //Get options from option block
                    ArrayList<String> questionOptions = ((QuestionBlock) currentQuestionBlock).getQuestionOptions();
                    questionDetails.put("option", questionOptions);

                }

                //inside this questionObject is another JSON object storing all the information
                JSONObject questionObject = new JSONObject();
                questionObject.put("question", questionDetails);

                QuestionListJSON.add(questionObject);
            }

        }

        System.out.println(QuestionListJSON.toString());
        try (FileWriter file = new FileWriter("QuestionListJSON.json")) {
            //We can write any JSONArray or JSONObject instance to the file
            file.write(QuestionListJSON.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }






    }


}

