package com.example.application.views.list;

import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.html.IFrame;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Test Page")
@Route(value = "test")
public class ListView extends VerticalLayout {


    IFrame frame = new IFrame();

    public ListView() {

        setSpacing(false);
        //configureButton();
        configureIframe();
        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
        add(frame);

    }
    public void configureIframe(){
        frame.addClassName("web-ide");
        frame.setSrc("https://www.online-ide.com/");
        frame.setWidthFull();
        frame.setHeight(620, Unit.PIXELS);
    }



}
