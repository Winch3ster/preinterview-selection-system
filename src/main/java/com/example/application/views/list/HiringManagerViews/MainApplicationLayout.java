package com.example.application.views.list.HiringManagerViews;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;

//Main Layout to be implemented by other page
public class MainApplicationLayout extends AppLayout {

    public MainApplicationLayout(){
        createHeader();
        createDrawer();
    }

    private void createHeader() {
        H1 title = new H1("Pre-Interview Selection System | Hiring Manager");
        title.addClassNames("text-l", "m-m"); //CSS style

        //horizontal layout to store the header
        //Drawer toggle comes with the icon
        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), title);
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.expand(title); //title will get as much space as possible
        header.setWidthFull();
        header.addClassNames("py-0", "px-m");

        addToNavbar(header);
    }

    private void createDrawer() {
        //RouterLink("Name to display", className)
        RouterLink listView = new RouterLink("List", ManagerDashboard.class); //link to other page/view
        listView.setHighlightCondition(HighlightConditions.sameLocation());

        RouterLink setQuestion = new RouterLink("Set Questions", SetQuestionPage.class);
        setQuestion.setHighlightCondition(HighlightConditions.sameLocation());

        addToDrawer(new VerticalLayout(
                listView,
                setQuestion


        ));
    }
}
