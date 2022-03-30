package com.example.application.views.list;

import com.example.application.views.list.CandidateViews.ExamPage;
import com.example.application.views.list.HiringManagerViews.HiringManagerLogin;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.Scanner;


@Route("")
@PageTitle("Home Page")
public class Home extends VerticalLayout {
    Dialog conformation = new Dialog();
    Button managerSignIn = new Button("Admin Login");
    Button submitButton =new Button("SUBMIT");
    VerticalLayout dialogLayout = new VerticalLayout();
    HorizontalLayout dialogButtonLayout = new HorizontalLayout();
    Button continueButton = new Button("Continue");
    Button cancelButton = new Button("Cancel");
    TextField name =new TextField();

    public Home(){
        // <-- GUI code starts here -->
        addClassName("home-page");
        setSizeFull();
        configureDialog();
        add(
                adminSignInLayout(),
                register()
        );
        // <-- GUI code ends here -->

    }

    // <-- GUI Interface methods -->
    public VerticalLayout register(){
        H1 title = new H1("Pre-Interview Selection System");

        //Text field for candidate name
        name.setLabel("Name");
        name.setRequiredIndicatorVisible(true);
        name.setPlaceholder("Full name");
        name.setAutofocus(true);
        name.setClearButtonVisible(true);

        //Text field for candidate's email
        EmailField email = new EmailField();
        email.setLabel("Email");
        email.setPlaceholder("Email address");
        email.setRequiredIndicatorVisible(true);
        email.setClearButtonVisible(true);

        //submit button
        submitButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        submitButton.addClickListener(click -> {
            conformation.open();

        });
        submitButton.addClickShortcut(Key.ENTER);

        VerticalLayout registerLayout = new VerticalLayout();
        registerLayout.add(title, name, email, submitButton);
        registerLayout.setAlignItems(Alignment.CENTER);

        return registerLayout;
    }

    public HorizontalLayout adminSignInLayout(){
        //Manager login button
        managerSignIn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        managerSignIn.addClickListener(click -> UI.getCurrent().navigate(HiringManagerLogin.class));

        //div container to store manager login button
        HorizontalLayout managerSignInContainer = new HorizontalLayout();
        managerSignInContainer.add(managerSignIn);

        return managerSignInContainer;
    }

    private void configureDialog(){
        conformation.add(
                new H2("Conformation"),
                new H3("Please make sure the data you entered match the one in your application form. \n" +
                        "Any foreign email addresses will be discarded. You may only take the test once." ),
                buttonGrid()
        );
    }

    private HorizontalLayout buttonGrid() {
        continueButton.addClickListener(event -> {
            //Remember to add a function to redirect user to another page
            //Notification.show(name.getValue());
            conformation.close();
        });

        continueButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        continueButton.addClickListener(event -> UI.getCurrent().navigate(ExamPage.class));
        cancelButton.addClickListener((buttonClickEvent -> conformation.close()));

        dialogButtonLayout.add(cancelButton, continueButton);
        return dialogButtonLayout;
    }

    // <-- GUI Interface methods -->


}

