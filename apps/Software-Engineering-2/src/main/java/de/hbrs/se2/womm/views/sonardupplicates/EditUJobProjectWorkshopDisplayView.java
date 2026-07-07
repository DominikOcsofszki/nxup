package de.hbrs.se2.womm.views.sonardupplicates;

import com.vaadin.flow.router.*;
import de.hbrs.se2.womm.config.SecurityService;
import de.hbrs.se2.womm.dtos.StelleDTO;
import de.hbrs.se2.womm.services.AboStudentUnternehmenService;
import de.hbrs.se2.womm.services.StelleService;
import de.hbrs.se2.womm.services.UnternehmenService;
import de.hbrs.se2.womm.views.layouts.ROUTING;
import de.hbrs.se2.womm.views.layouts.UnternehmenLayout;
import de.hbrs.se2.womm.views.unternehmen.UStelleAnzeigeErstellenView;
import jakarta.annotation.security.RolesAllowed;

import java.util.Optional;
@Route(value = ROUTING.UNTERNEHMEN.EditUJobProjectWorkshopDisplayView, layout = UnternehmenLayout.class)
@RolesAllowed({"STUDENT", "ADMIN"})
@PageTitle("EditUJobProjectWorkshopDisplayView")
public class EditUJobProjectWorkshopDisplayView extends UStelleAnzeigeErstellenView implements HasUrlParameter<String> {
    StelleService stelleService;
    public EditUJobProjectWorkshopDisplayView(StelleService stelleService, UnternehmenService unternehmenService, SecurityService securityService, AboStudentUnternehmenService aboStudentUnternehmenService ) {
        super(stelleService, unternehmenService, securityService, aboStudentUnternehmenService);
        this.stelleService = stelleService;
    }

    @Override
    public void setParameter(BeforeEvent event, @OptionalParameter String parameter) {
            Optional<StelleDTO> getCurrentStelleForEdit = stelleService.getById(Long.valueOf(parameter));
            if(getCurrentStelleForEdit.isPresent())setUpFieldForEdit(getCurrentStelleForEdit.get());
        }
    }

