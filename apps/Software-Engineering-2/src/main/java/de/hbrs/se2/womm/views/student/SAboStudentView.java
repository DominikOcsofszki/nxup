package de.hbrs.se2.womm.views.student;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import de.hbrs.se2.womm.config.SecurityService;
import de.hbrs.se2.womm.dtos.AboDTO;
import de.hbrs.se2.womm.services.AboStudentUnternehmenService;
import de.hbrs.se2.womm.views.components.GridFilterAboFromStudent;
import de.hbrs.se2.womm.views.layouts.AViewWomm;
import de.hbrs.se2.womm.views.layouts.StudentLayout;
import jakarta.annotation.security.RolesAllowed;

import java.util.List;

@Route(value = "SAboStudentView", layout = StudentLayout.class)
@RolesAllowed({"STUDENT","ADMIN"})
@PageTitle("AboStudentView")
public class SAboStudentView extends  AViewWomm{

    List<AboDTO> AboDTOList;
    long nutzerID;
    GridFilterAboFromStudent gridFilterAboFromStudent = new GridFilterAboFromStudent();
    public SAboStudentView(AboStudentUnternehmenService aboStudentUnternehmenService, SecurityService securityService) {
        nutzerID = securityService.getLoggedInNutzerID();
        AboDTOList = aboStudentUnternehmenService.getByNutzerId(nutzerID);
        H1 abonnements = getWommBuilder().H1.create("Subscriptions");
        add(abonnements);
        gridFilterAboFromStudent.setUpFromOutside(AboDTOList);
        add(gridFilterAboFromStudent);
    }

}
