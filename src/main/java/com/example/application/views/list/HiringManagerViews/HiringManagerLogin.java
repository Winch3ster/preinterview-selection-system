package com.example.application.views.list.HiringManagerViews;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterListener;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;


@Route("login")
@PageTitle("Login | Pre-Interview Test")
public class HiringManagerLogin extends VerticalLayout implements BeforeEnterListener {

    private final LoginForm loginForm =new LoginForm();

    public HiringManagerLogin(){
        addClassName("login-view");
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        loginForm.setAction("login");

        add(
                new H1("Pre-Interview Selection System"),
                loginForm
        );

    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if(beforeEnterEvent.getLocation().getQueryParameters()
                .getParameters().containsKey("error")){
            loginForm.setError(true);
        }
    }
}
