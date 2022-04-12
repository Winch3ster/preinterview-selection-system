package com.example.application.data.Repository;

import com.example.application.data.entity.Contact;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//A contact class to store all the candidates' information.
public class ContactRepository {
    //static methods and variable can be accessed without creating an object

    /*
    In the Hiring manager view, there is a function to check if this class is updated
    If there is an update, the function will run
     */
    static public boolean updated = false;

    static public ArrayList<Contact> contacts = new ArrayList<>(); //Array list to store all the contact ibjects

    //update the list
    static public void updateList(ArrayList<Contact> contact){
        contacts = contact;
        updated = true;
    }
}
