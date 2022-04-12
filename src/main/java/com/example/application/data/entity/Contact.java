package com.example.application.data.entity;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

//Contact class to store the candidate's information
public class Contact {

    @NotEmpty
    private String candidateName = "";

    @Email
    @NotEmpty
    private String email = "";

    @NotEmpty
    private int candidateScore =0;

    @NotEmpty
    private boolean takenTest = true;

    public Contact(String name, String email, int score){
        this.candidateName = name;
        this.email = email;
        this.candidateScore = score;

    }

    //Getters and setters methods
    public int getCandidateScore(){return candidateScore;}

    public void setCandidateScore(int candidateScore) {this.candidateScore = candidateScore;}

    public String getCandidateName() {
        return candidateName;
    }

    public void setCandidateName(String candidateName) {
        this.candidateName = candidateName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean getTestStatus() {
        return takenTest;
    }

    public void setTestTaken(boolean takenTest) { this.takenTest = takenTest; }
}
