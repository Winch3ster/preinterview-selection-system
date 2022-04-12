package com.example.application.views.list.CandidateViews;


import com.vaadin.flow.component.radiobutton.RadioButtonGroup;


import java.util.ArrayList;

//Inherit attributes from the parent class (QuestionBlockCandidate)
public class QuestionBlockChoicesCandidate extends QuestionBlockCandidate{

    //Radio button to display the choices of multiple choice or true false question
    RadioButtonGroup<String> options = new RadioButtonGroup<>();

    QuestionBlockChoicesCandidate(String questionText, String answer, ArrayList<String> optionsArray){
        this.setAnswer(answer);
        this.questionField.setText(questionText);
        this.options.setItems(optionsArray);

        //Get the value picked by the user and set it to userAnswer variable
        options.addValueChangeListener(answerPicked -> this.setUserAnswer(options.getValue()));

        this.questionBlock.add(questionField, options);

        add(questionBlock);
    }


}
