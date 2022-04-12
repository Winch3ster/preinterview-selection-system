package com.example.application.views.list.HiringManagerViews;


import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;


@Route("login")
@PageTitle("Login | Pre-Interview Test")

//Inherit login form from Composite<LoginOverlay> class
public class HiringManagerLogin extends Composite<LoginOverlay> {

    public HiringManagerLogin(){

        //Setting up the login form
        LoginOverlay loginOverlay = getContent();
        loginOverlay.setTitle("Pre-Interview Selection System");
        loginOverlay.setDescription("Hiring manager log in");
        loginOverlay.setOpened(true);

        //Validate entered credentials
        loginOverlay.addLoginListener(login -> {
            if ("user".equals(login.getUsername()) && "user".equals(login.getPassword())){
                UI.getCurrent().navigate("managerPage");
            } else {
                loginOverlay.setError(true);
            }
        });

    }

}
