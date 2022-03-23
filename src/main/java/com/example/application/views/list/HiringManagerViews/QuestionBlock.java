package com.example.application.views.list.HiringManagerViews;


import com.vaadin.flow.component.Component;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.radiobutton.RadioGroupVariant;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;


import javax.validation.constraints.NotNull;
import java.util.ArrayList;

public class QuestionBlock extends VerticalLayout {


    @NotNull
    private String questionText;

    @NotNull
    private String answer;

    private ArrayList<String> multipleChoiceOptions = new ArrayList<>();
    //private String[] multipleChoiceOptions = new String[4];
    private String questionType;
    //Question types --> "Multiple Choice", "True False", "Code"

    //Variable
    private boolean toBeRemoved;


    //Component for questions
    private TextArea questionField = new TextArea();
    private Button removeButton =new Button();
    private Select<String> questionSelector = new Select<>();
    private VerticalLayout questionBlock = new VerticalLayout();
    private VerticalLayout optionUserKeyIn = new VerticalLayout();
    private RadioButtonGroup<String> optionDisplay = new RadioButtonGroup<>();
    private HorizontalLayout optionBlock = new HorizontalLayout();



    QuestionBlock(){
        toBeRemoved = false; //question is not to be removed upon initialization

        this.removeButton.addClassName("remove-button");
        this.questionBlock.addClassName("question-block");
        this.optionDisplay.addThemeVariants(RadioGroupVariant.LUMO_VERTICAL);
        this.optionDisplay.setLabel("Select the answer here");


        optionDisplay.addValueChangeListener(event -> {
            answer = this.optionDisplay.getValue();
        });

        setAlignItems(Alignment.CENTER);
        setWidthFull(); //set width of the question block to size of web frame

        add(

                generateQuestionBlock());


    }

    private Component generateQuestionBlock() {
        //textField for Hiring Manager to inout the question statement
        questionField.setWidthFull();
        questionField.setPlaceholder("Enter Question here");
        questionField.addValueChangeListener(type -> this.questionText = questionField.getValue());


        //Drop down for question type
        questionSelector.setPlaceholder("Select Question Type");
        questionSelector.setItems("Multiple Choice", "True False", "Code");
        questionSelector.addValueChangeListener(event -> {
            questionType = questionSelector.getValue();
            //Notification.show(questionSelector.getValue());
            //Generate appropriate option based on the question type selected
            generateOptionBlock(questionType);

        });

        questionBlock.setVisible(true);
        questionBlock.add(
                removeQuestionButton(),
                questionField,
                questionSelector,
                optionBlock);

        return questionBlock;
    }

    private void generateOptionBlock(String questionTypes) {
        optionBlock.removeAll();
        optionUserKeyIn.removeAll();
        optionDisplay.removeAll();
        optionDisplay.setItems("", "", "", "");

        if(questionTypes.equals("Multiple Choice")){
            RadioButtonGroup<String> options = new RadioButtonGroup<>();

            for (int i =0; i < 4; i++){
                multipleChoiceOptions.add(" ");
            }

            //questionType = "Multiple Choice";
            TextField choice1 = new TextField();
            choice1.setValueChangeMode(ValueChangeMode.EAGER);
            choice1.addValueChangeListener(userInput -> {
                multipleChoiceOptions.set(0, choice1.getValue());
                optionDisplay.setItems(multipleChoiceOptions);
            });

            TextField choice2 = new TextField();
            choice2.setValueChangeMode(ValueChangeMode.EAGER);
            choice2.addValueChangeListener(userInput ->{
                        multipleChoiceOptions.set( 1, choice2.getValue());
                        optionDisplay.setItems(multipleChoiceOptions);
                    }
            );

            TextField choice3 = new TextField();
            choice3.setValueChangeMode(ValueChangeMode.EAGER);
            choice3.addValueChangeListener(userInput -> {
                multipleChoiceOptions.set(2, choice3.getValue());
                optionDisplay.setItems(multipleChoiceOptions);
            });

            TextField choice4 = new TextField();
            choice4.setValueChangeMode(ValueChangeMode.EAGER);
            choice4.addValueChangeListener(userInput -> {
                multipleChoiceOptions.set(3,choice4.getValue());
                optionDisplay.setItems(multipleChoiceOptions);
            });


            optionUserKeyIn.add(choice1,  choice2, choice3, choice4);
            optionBlock.add(optionUserKeyIn, optionDisplay);
            optionDisplay.addValueChangeListener(clicked -> this.answer = optionDisplay.getValue());


        } else if(questionTypes.equals("True False")){
            RadioButtonGroup<String> true_falseChoice = new RadioButtonGroup<>();
            true_falseChoice.setLabel("Select the answer here");
            true_falseChoice.setItems("True", "False");
            true_falseChoice.addValueChangeListener(event -> this.answer = true_falseChoice.getValue());
            //optionUserKeyIn.add(true_falseChoice);
            optionBlock.add(true_falseChoice);

        } else {
            TextField codingSectionAnswerField = new TextField();
            codingSectionAnswerField.setLabel("Expected output from user's code: ");
            codingSectionAnswerField.setPlaceholder("Expected one output only.");
            codingSectionAnswerField.setValueChangeMode(ValueChangeMode.LAZY);
            codingSectionAnswerField.addValueChangeListener(valueChanged -> this.answer = codingSectionAnswerField.getValue());
            optionBlock.add(codingSectionAnswerField);
        }

    }


    private Button removeQuestionButton(){
        //Remove button
        removeButton = new Button("Remove");
        removeButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR);

        //Click listener
        removeButton.addClickListener(click -> {
            toBeRemoved = true;
            this.questionBlock.add(
                    new H2("Question removed"),
                    new Image("images/removed-questionBlock.png", "Removed")
            );

            //Remove the question block components
            questionBlock.remove(
                    this.removeButton,
                    questionField,
                    questionSelector,
                    optionBlock);



            //Notification.show("The value is " + this.toBeRemoved);
        });
        return removeButton;
    }


    //get the state of the question block
    public boolean getState(){
        return toBeRemoved;
    }


    //Newly added methods
    public String getAnswer(){
        return answer;
    }

    public String getQuestionType(){
        return questionType;
    }

    public ArrayList<String> getQuestionOptions(){
        if((this.getQuestionType()).equals("Multiple Choice")){
            return getMultipleChoiceOptions();
        } else {
            return getTrueFalseOption();
        }
    }

    private ArrayList<String> getTrueFalseOption() {
        ArrayList<String> trueFalse = new ArrayList<>();
        trueFalse.add("True");
        trueFalse.add("False");

        return trueFalse;
    }

    private ArrayList<String> getMultipleChoiceOptions() {
        return multipleChoiceOptions;
    }

    public String getQuestionText(){
        return questionText;
    }


}
