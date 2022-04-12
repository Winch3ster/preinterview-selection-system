package com.example.application.views.list;


import com.example.application.views.list.CandidateViews.ExamPage;
import com.example.application.views.list.HiringManagerViews.HiringManagerLogin;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;


@Route("")
@PageTitle("Home Page")

//GUI home page
public class Home extends VerticalLayout {

    private Dialog conformation = new Dialog();
    private Button managerSignIn = new Button("Admin Login");
    private Button submitButton =new Button("SUBMIT");
    private VerticalLayout dialogLayout = new VerticalLayout();
    private HorizontalLayout dialogButtonLayout = new HorizontalLayout();
    private Button continueButton = new Button("Continue");
    private Button cancelButton = new Button("Cancel");
    private TextField name =new TextField();
    private EmailField email = new EmailField();
    //"images/removed-questionBlock.png"
    private Image img = new Image("images/logo_meet_2020q4_color_2x_web_96dp.png", "Google meet");

    private Anchor url = new Anchor("https://meet.google.com/");


    private Button gmeetButton = new Button(img);
    static public String candidateName;
    static public String candidateEmail;
    public Home(){
        //set the GMeet logo size
        img.setWidth(50 ,Unit.PIXELS);
        img.setHeight(50, Unit.PIXELS);

        addClassName("home-page");
        setSizeFull();
        configureDialog();
        add(
                headerLayout(),
                register()
        );


    }

    //Candidate registration fields
    public VerticalLayout register(){
        H1 title = new H1("Pre-Interview Selection System");
        //Text field for candidate name
        name.setLabel("Name");
        name.setRequiredIndicatorVisible(true);
        name.setPlaceholder("Full name");
        name.setAutofocus(true);
        name.setClearButtonVisible(true);

        //Text field for candidate's email

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

    public HorizontalLayout headerLayout(){
        //Manager login button
        managerSignIn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        managerSignIn.addClickListener(click -> UI.getCurrent().navigate(HiringManagerLogin.class));

        //Gmeet link
        url.setTarget("_blank"); //this is to open in a new tab
        url.add(gmeetButton); //add your button here

        //div container to store manager login button
        HorizontalLayout managerSignInContainer = new HorizontalLayout();
        managerSignInContainer.add(managerSignIn, url);

        return managerSignInContainer;
    }

    private void configureDialog(){
        conformation.add(
                new H2("Confirmation"),
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
        continueButton.addClickListener(event -> {
            candidateName = name.getValue();
            candidateEmail =email.getValue();

            //new ExamPage(name.getValue(), email.getValue());
            UI.getCurrent().navigate(ExamPage.class);
        });
        cancelButton.addClickListener((buttonClickEvent -> conformation.close()));

        dialogButtonLayout.add(cancelButton, continueButton);
        return dialogButtonLayout;
    }

}

