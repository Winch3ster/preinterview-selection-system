package com.example.application.views.list.CandidateViews;

import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class QuestionBlockCandidate extends VerticalLayout {

    //Question Block attributes
    private String answer;
    private String userAnswer;
    public VerticalLayout questionBlock = new VerticalLayout();
    public Label questionField = new Label();

    //Question class methods
    //getters
    public String getAnswer(){
        return answer;
    }
    public String getUserAnswer(){
        return userAnswer;
    }

    //setters
    public void setAnswer(String answer){
        this.answer = answer;
    }

    public void setUserAnswer(String userAns){
        this.userAnswer = userAns;
    }

}
