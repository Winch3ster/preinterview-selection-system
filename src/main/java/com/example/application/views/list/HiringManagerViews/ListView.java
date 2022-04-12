package com.example.application.views.list.HiringManagerViews;

import com.example.application.data.Repository.ContactRepository;
import com.example.application.data.entity.Contact;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.List;

@PageTitle("Dashboard | Hiring Manager")
@Route(value = "managerPage", layout = MainApplicationLayout.class)//ListView will be loaded into the MainLayout class
public class ListView extends VerticalLayout {

    //Grid to display candidates' information
    Grid<Contact> grid = new Grid<>(Contact.class);
    private Button removeCandidatesButton = new Button("Remove All");

    //Candidate list from the contactRepository class
    List<Contact> people = ContactRepository.contacts;

    //Class constructor
    public ListView(){

        configureRemoveButton();
        addClassName("list-view");
        setSizeFull();
        //configureGrid();
        grid.setItems(people);
        updateGridData();
        add(
                getToolBar(),
                grid
        );
    }

    private void updateGridData() {
        //There is a changes to the ContactRepository's arrayList, update the new data to the grid
        if(ContactRepository.updated){
            people = ContactRepository.contacts;
            grid.setItems(people);
        }
    }


    //customise the toolbar for removing all the candidates' information
    private Component getToolBar() {
        removeCandidatesButton.addThemeVariants(ButtonVariant.LUMO_ERROR);

        //horizontal layout to wrap these filter text and button together o they are next to each other
        HorizontalLayout toolbar = new HorizontalLayout(new H3("Remove all candidates from list"), removeCandidatesButton);
        toolbar.setAlignItems(Alignment.BASELINE);
        toolbar.addClassName("toolbar");

        return toolbar;
    }


    private void configureRemoveButton() {
        removeCandidatesButton.addClickListener(clicked ->{
            people.clear(); //Remove all the contact object from ContactRepository class
            updateGridData();
            grid.setItems(people);
        }
        );
    }

}
