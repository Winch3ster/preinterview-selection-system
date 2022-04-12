package com.example.application.views.list.TerminalCode;

import java.util.ArrayList;

//Question block class for CLI mode, new class is required as the no mapping to UX component will be implemented

public class QuestionBLockCLI {

    //Question attributes
    private String questionType;
    private String answer;
    private String questionStatement;
    private ArrayList<String> options;

    QuestionBLockCLI(String questionType, String questionStatement,String answer, ArrayList<String> options ){
        this.questionType = questionType;
        this.questionStatement = questionStatement;
        this.answer = answer;
        this.options = options;
    }

    //Getters method
    public String getAnswer(){
        return answer;
    }
    public String getQuestionStatement(){
        return questionStatement;
    }
    public ArrayList<String> getOptions(){
        return options;
    }
    public String getQuestionType(){
        return questionType;
    }

}
