package com.example.application.views.list.HiringManagerViews;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value= "interviewDatePage", layout = MainApplicationLayout.class)
public class InterviewDatePage extends VerticalLayout {

    VerticalLayout interviewDatePageLayout = new VerticalLayout();
    DatePicker datePicker = new DatePicker("Interview date");
    Button uploadButton = new Button("UPLOAD");

    InterviewDatePage(){
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        configureInterviewDatePageLayout();
        configureUploadButton();
        add(
                interviewDatePageLayout
                );
    }

    private void configureInterviewDatePageLayout() {
        interviewDatePageLayout.add(datePicker, uploadButton);
        interviewDatePageLayout.setAlignItems(Alignment.CENTER);
    }

    private void configureUploadButton() {
        uploadButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        uploadButton.addClickListener(event -> {
            Notification.show("Button clicked!");
            interviewDatePageLayout.removeAll();
            interviewDatePageLayout.add(new H1("Questions and interview date uploaded!"));
        });
    }


}
