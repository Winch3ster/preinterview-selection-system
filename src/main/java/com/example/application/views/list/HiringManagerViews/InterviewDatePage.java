package com.example.application.views.list.HiringManagerViews;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

@Route(value= "interviewDatePage", layout = MainApplicationLayout.class)
public class InterviewDatePage extends VerticalLayout {

    private VerticalLayout interviewDatePageLayout = new VerticalLayout();
    private DatePicker datePicker = new DatePicker("Interview date");
    private Button uploadButton = new Button("UPLOAD");
    private LocalDate interviewDate;

    //Class constructor
    InterviewDatePage(){
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        configureInterviewDatePageLayout();
        configureUploadButton();
        add(
                interviewDatePageLayout
                );
    }//end of class constructor


    private void configureInterviewDatePageLayout() {
        interviewDatePageLayout.add(datePicker, uploadButton);
        interviewDatePageLayout.setAlignItems(Alignment.CENTER);
    }

    private void configureUploadButton() {
        uploadButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        //Save the interview date when user clicked on the upload button
        uploadButton.addClickListener(event -> {
            saveInterviewDateJSON();
            interviewDatePageLayout.removeAll();
            interviewDatePageLayout.add(new H1("Interview date uploaded / updated!"));
        });
    }


    //Write the interview date to interviewDate.json
    private void saveInterviewDateJSON() {
        //Create a JSON object to store the interview date
        JSONObject interviewDateJSON = new JSONObject();
        interviewDateJSON.put("date" , interviewDate);
        JSONObject json = new JSONObject();
        json.put("interviewDate", interviewDateJSON);

        try (FileWriter file = new FileWriter("interviewDate.json")) {
            //We can write any JSONArray or JSONObject instance to the file
            file.write(json.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public LocalDate getInterviewDate(){
        return interviewDate;
    }

}
