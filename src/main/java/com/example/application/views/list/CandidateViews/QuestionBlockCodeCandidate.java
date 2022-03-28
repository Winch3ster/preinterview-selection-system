package com.example.application.views.list.CandidateViews;

import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;

public class QuestionBlockCodeCandidate extends QuestionBlockCandidate{
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
