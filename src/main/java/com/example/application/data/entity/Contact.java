package com.example.application.data.entity;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;


public class Contact {

    @NotEmpty
    private String candidateName = "";

    @Email
    @NotEmpty
    private String email = "";

    @NotEmpty
    private boolean takenTest = true;


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

    public void setTestStatus(boolean takenTest) {
        this.takenTest = takenTest;
    } //vaadin will automatically interpret this as the name for the column
}
