package de.hbrs.se2.womm.views.student;

import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import de.hbrs.se2.womm.config.SecurityService;
import de.hbrs.se2.womm.services.BenachrichtigungService;
import de.hbrs.se2.womm.services.BewerbungService;
import de.hbrs.se2.womm.services.StelleService;
import de.hbrs.se2.womm.services.StudentService;
import de.hbrs.se2.womm.views.layouts.ROUTING;
import de.hbrs.se2.womm.views.layouts.StudentLayout;
import de.hbrs.se2.womm.views.sonardupplicates.BJobProjectWorkshopDisplayView;
import jakarta.annotation.security.RolesAllowed;

@Route(value = ROUTING.STUDENT.SJobProjectWorkshopDisplayView, layout = StudentLayout.class)
@RolesAllowed({"STUDENT", "ADMIN"})
@PageTitle("JobProjectWorkshopDisplayView")
public class SJobProjectWorkshopDisplayView extends BJobProjectWorkshopDisplayView implements HasUrlParameter<Long> {
    public SJobProjectWorkshopDisplayView(StelleService stelleService, SecurityService securityService,
                                          BewerbungService bewerbungService, StudentService studentService,
    BenachrichtigungService benachrichtigungService) {
        super(stelleService, securityService, bewerbungService, studentService,benachrichtigungService);
    }
}
