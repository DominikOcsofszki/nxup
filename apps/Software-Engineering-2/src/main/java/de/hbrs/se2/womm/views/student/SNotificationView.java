package de.hbrs.se2.womm.views.student;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import de.hbrs.se2.womm.config.SecurityService;
import de.hbrs.se2.womm.dtos.AboDTO;
import de.hbrs.se2.womm.dtos.NutzerDTO;
import de.hbrs.se2.womm.dtos.StelleDTO;
import de.hbrs.se2.womm.dtos.UnternehmenDTO;
import de.hbrs.se2.womm.services.AboStudentUnternehmenService;
import de.hbrs.se2.womm.services.StelleService;
import de.hbrs.se2.womm.views.components.GridFilterStelle;
import de.hbrs.se2.womm.views.layouts.AViewWomm;
import de.hbrs.se2.womm.views.layouts.ROUTING;
import de.hbrs.se2.womm.views.layouts.StudentLayout;
import jakarta.annotation.security.RolesAllowed;

import java.util.List;

@Route(value = ROUTING.STUDENT.SNotificationView, layout = StudentLayout.class)
@RolesAllowed({"STUDENT", "ADMIN"})
@PageTitle("NotificationView")
public class SNotificationView extends AViewWomm {
    GridFilterStelle gridFilterStelle = new GridFilterStelle();
    StelleDTO stelleDTO;
    StelleService stelleService;
    SecurityService securityService;
    AboStudentUnternehmenService aboStudentUnternehmenService;
    List<AboDTO> aboDTOList;
    List<UnternehmenDTO> unternehmenDTOList;
    List<StelleDTO> stelleDTOList;

    public SNotificationView(StelleService stelleService, SecurityService securityService,
                             AboStudentUnternehmenService aboStudentUnternehmenService) {
        this.aboStudentUnternehmenService = aboStudentUnternehmenService;
        this.stelleService = stelleService;
        this.securityService = securityService;
        this.aboDTOList = aboStudentUnternehmenService.getByNutzerId(securityService.getLoggedInNutzerID());
        this.unternehmenDTOList = aboDTOList.stream().map(AboDTO::getUnternehmen).toList();
        this.stelleDTOList = unternehmenDTOList.stream().
                map(UnternehmenDTO::getNutzer).
                map(NutzerDTO::getNutzerId).
                map(stelleService::getByNutzerId).flatMap(List::stream).toList();

        setUpHeader();
        gridFilterStelle.setUpFromOutside(stelleDTOList);
        this.gridFilterStelle.setColumnClickListener(ROUTING.STUDENT.SJobProjectWorkshopDisplayView);
        add(gridFilterStelle);
    }

    void setUpHeader() {
        H2 headerText = getWommBuilder().H2.create("New Jobs from your subscribed companies");
        add(headerText);
    }


}
