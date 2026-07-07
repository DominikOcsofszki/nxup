package de.hbrs.se2.womm.views.student;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import de.hbrs.se2.womm.config.SecurityService;
import de.hbrs.se2.womm.dtos.StudentDTO;
import de.hbrs.se2.womm.services.StelleService;
import de.hbrs.se2.womm.services.StudentService;
import de.hbrs.se2.womm.views.components.GridFilterStelle;
import de.hbrs.se2.womm.views.layouts.AViewWomm;
import de.hbrs.se2.womm.views.layouts.ROUTING;
import de.hbrs.se2.womm.views.layouts.StudentLayout;
import jakarta.annotation.security.RolesAllowed;

@Route(value = ROUTING.STUDENT.SHomepageStudentView, layout = StudentLayout.class)
@RolesAllowed({"STUDENT", "ADMIN"})
@PageTitle("HomepageStudentView")
public class SHomepageStudentView extends AViewWomm {
    StelleService stelleService;
    StudentDTO studentDTO;
    private long aktuelleNutzerID;
    private GridFilterStelle gridFilterStelle;

    public SHomepageStudentView(StelleService stelleService, StudentService studentService, SecurityService securityService) {
        super();
        this.stelleService = stelleService;
        this.aktuelleNutzerID = securityService.getLoggedInNutzerID();
        this.studentDTO = studentService.getByNutzerId(aktuelleNutzerID);
        this.gridFilterStelle = new GridFilterStelle();
        this.gridFilterStelle.setUpFromOutside(stelleService.getAllByFilter("",""));
        this.gridFilterStelle.setColumnClickListener(ROUTING.STUDENT.SJobProjectWorkshopDisplayView);
        setUpHeader();
        setUpSearchFields();
        add(this.gridFilterStelle);
    }

    private void setUpHeader() {
        VerticalLayout header = new VerticalLayout();
        String welcome = getWommBuilder().translateText("Welcome ");
        H1 headerText = new H1(welcome + studentDTO.getStudentVorname() + " " + studentDTO.getStudentName());
        H2 text = getWommBuilder().H2.create("Here you can see all open advertisements");
        header.add(headerText,text);
        add(header);
    }
    private void setUpSearchFields() {
    /*
        VerticalLayout searchResults = new VerticalLayout();
        searchResults.add(new H2("Search Results"));
        searchResults.add(new Hr()); */
    }
}
