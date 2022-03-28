package com.example.application.views.list.HiringManagerViews;

import com.example.application.data.entity.Contact;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Dashboard | Hiring Manager")
@Route(value = "managerPage", layout = MainApplicationLayout.class)//ListView will be loaded into the MainLayout class
public class ManagerDashboard extends VerticalLayout {

    Grid<Contact> grid = new Grid<>(Contact.class); //Display candidates' information
    TextField filterText = new TextField();

    //Class constructor
    public ManagerDashboard(){
        addClassName("list-view");
        setSizeFull();
        configureGrid();

        add(
                getToolBar(),
                grid
        );





    }


    //customise the toolbar for searching and adding people
    private Component getToolBar() {
        filterText.setPlaceholder("Filter by Name");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY); //Not fetching from database from every keystroke

        Button addCandidateButton = new Button("Add Contact");

        //horizontal layout to wrap these filter text and button together o they are next to each other
        HorizontalLayout toolbar = new HorizontalLayout(filterText, addCandidateButton);
        toolbar.addClassName("toolbar");

        return toolbar;
    }


    //Customize how the grid will look like
    private void configureGrid() {
        grid.addClassName("contact-grid");
        grid.setSizeFull();
        //grid.setColumns("candidateName", "email", "takenTest");
        grid.getColumns().forEach(column -> column.setAutoWidth(true));
    }



}
