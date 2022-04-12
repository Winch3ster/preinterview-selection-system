package com.example.application.views.list.CandidateViews;

import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;

//Inherit attributes from the parent class (QuestionBlockCandidate)
public class QuestionBlockCodeCandidate extends QuestionBlockCandidate{

    //Instead of radio button, a text field will be instantiated to take the user input
    private TextField candidateAnswerSpace = new TextField();

    //Beginning of class constructor
    QuestionBlockCodeCandidate(String question, String answer){
        this.questionField.setText(question);
        this.setAnswer(answer); //answer inherited from parent class (QuestionBlockCandidate)

        candidateAnswerSpace.setLabel("Enter your answer here: ");
        candidateAnswerSpace.setValueChangeMode(ValueChangeMode.LAZY);
        candidateAnswerSpace.addValueChangeListener(answered ->
                this.setUserAnswer(candidateAnswerSpace.getValue()) //set candidate answer
        );

        this.questionBlock.add(questionField, candidateAnswerSpace);

        add(questionBlock); //add question block to Vertical layout inherited from parent class

    }//End of class constructor



}
