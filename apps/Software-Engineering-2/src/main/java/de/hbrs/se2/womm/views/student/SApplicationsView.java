package de.hbrs.se2.womm.views.student;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.*;

import de.hbrs.se2.womm.config.SecurityService;
import de.hbrs.se2.womm.dtos.BewerbungDTO;
import de.hbrs.se2.womm.dtos.StudentDTO;
import de.hbrs.se2.womm.services.BewerbungService;
import de.hbrs.se2.womm.services.StelleService;
import de.hbrs.se2.womm.services.StudentService;
import de.hbrs.se2.womm.views.components.GridFilterBewerbungStudents;
import de.hbrs.se2.womm.views.extra.TEMPLATE;
import de.hbrs.se2.womm.views.extra.VaadinBuilderWomm;
import de.hbrs.se2.womm.views.layouts.ROUTING;
import de.hbrs.se2.womm.views.layouts.StudentLayout;
import jakarta.annotation.security.RolesAllowed;

import java.util.Arrays;
import java.util.List;

@Route(value = ROUTING.STUDENT.SApplicationsView, layout = StudentLayout.class)
@RolesAllowed({"STUDENT", "ADMIN"})
@PageTitle("ApplicationsView")
public class SApplicationsView extends VerticalLayout {

    StelleService stelleService;
    BewerbungService bewerbungService;
    StudentDTO studentDTO;
    List<BewerbungDTO> listOfBewerbungDTO;
    private long aktuelleNutzerID;


    public SApplicationsView(BewerbungService bewerbungService, StelleService stelleService, StudentService studentService, SecurityService securityService) {
        this.stelleService = stelleService;
        this.bewerbungService = bewerbungService;
        this.aktuelleNutzerID = securityService.getLoggedInNutzerID();
        this.studentDTO = studentService.getByNutzerId(aktuelleNutzerID);
        this.listOfBewerbungDTO = bewerbungService.getByStudentNutzerId(aktuelleNutzerID);
        if (!listOfBewerbungDTO.isEmpty()) {
            // System.out.println(this.listOfBewerbungDTO.get(0).getBewerbungStelle().getStelleTitel());
        }
        GridFilterBewerbungStudents gridFilterBewerbungStudent = new GridFilterBewerbungStudents();
        gridFilterBewerbungStudent.setUpFromOutside(listOfBewerbungDTO);
        gridFilterBewerbungStudent.setColumnClickListener();


        setUpHeader();

        add(gridFilterBewerbungStudent);

    }


    private void setUpHeader() {

        HorizontalLayout header = new HorizontalLayout();

        //Ueberschrift
        H1 headerText = new H1(VaadinBuilderWomm.translateTextStatic("Applications"));
        header.add(headerText);

        add(header);

    }

}
