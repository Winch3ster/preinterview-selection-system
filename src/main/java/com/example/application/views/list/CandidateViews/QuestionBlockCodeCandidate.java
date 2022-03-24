package com.example.application.views.list.CandidateViews;

import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;

public class QuestionBlockCodeCandidate extends QuestionBlockCandidate{
    TextField candidateAnswerSpace = new TextField();

    QuestionBlockCodeCandidate(String question, String answer){
        this.questionField.setText(question);
        this.setAnswer(answer);
        candidateAnswerSpace.setLabel("Enter your answer here: ");
        candidateAnswerSpace.setValueChangeMode(ValueChangeMode.LAZY);
        candidateAnswerSpace.addValueChangeListener(answered ->
                this.setUserAnswer(candidateAnswerSpace.getValue())
                );


        this.questionBlock.add(questionField, candidateAnswerSpace);

        add(questionBlock);

    }



}
