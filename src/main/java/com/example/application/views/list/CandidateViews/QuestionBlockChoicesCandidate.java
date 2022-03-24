package com.example.application.views.list.CandidateViews;

import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;


import java.util.ArrayList;

public class QuestionBlockChoicesCandidate extends QuestionBlockCandidate{

    RadioButtonGroup<String> options = new RadioButtonGroup<>();


    QuestionBlockChoicesCandidate(String questionText, String answer, ArrayList<String> optionsArray){
        //System.out.println("QuestionBlockChoicesCandidate is instantiated from the class");

        this.setAnswer(answer);
        this.questionField.setText(questionText);
        this.options.setItems(optionsArray);
        options.addValueChangeListener(answerPicked -> this.setUserAnswer(options.getValue()));


        this.questionBlock.add(questionField, options);


        add(questionBlock);

    }


}
