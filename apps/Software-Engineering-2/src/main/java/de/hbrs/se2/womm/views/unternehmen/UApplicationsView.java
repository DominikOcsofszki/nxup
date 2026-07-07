package de.hbrs.se2.womm.views.unternehmen;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import de.hbrs.se2.womm.config.SecurityService;
import de.hbrs.se2.womm.dtos.BewerbungDTO;
import de.hbrs.se2.womm.dtos.UnternehmenDTO;
import de.hbrs.se2.womm.services.BewerbungService;
import de.hbrs.se2.womm.services.UnternehmenService;
import de.hbrs.se2.womm.views.components.GridFilterBewerbung;
import de.hbrs.se2.womm.views.layouts.AViewWomm;
import de.hbrs.se2.womm.views.layouts.ROUTING;
import de.hbrs.se2.womm.views.layouts.UnternehmenLayout;
import jakarta.annotation.security.RolesAllowed;

import java.util.List;

@Route(value = ROUTING.UNTERNEHMEN.UApplicationsView, layout = UnternehmenLayout.class)
@RolesAllowed({"UNTERNEHMEN","ADMIN"})
@PageTitle("ApplicationsView")
public class UApplicationsView extends AViewWomm {
    UnternehmenService unternehmenService;
    SecurityService securityService;
    BewerbungService bewerbungService;
    GridFilterBewerbung gridFilterBewerbung;
    public UApplicationsView(BewerbungService bewerbungService,
                             SecurityService securityService,
                             UnternehmenService unternehmenService) {
        this.securityService = securityService;
        this.bewerbungService = bewerbungService;
        this.unternehmenService = unternehmenService;
        this.gridFilterBewerbung = new GridFilterBewerbung();

        add(getWommBuilder().H1.create("Current Applications"));

        long nutzerId = securityService.getLoggedInNutzerID();
        UnternehmenDTO unternehmen = unternehmenService.getByNutzerId(nutzerId);
        long unternehmenId = unternehmen.getUnternehmenId();

        List<BewerbungDTO> bewerbungen = bewerbungService.getByUnternehmenNutzerId(unternehmenId);
        gridFilterBewerbung.setUpFromOutside(bewerbungen);
        gridFilterBewerbung.setColumnClickListener(ROUTING.UNTERNEHMEN.UApplicationView);
        add(gridFilterBewerbung);
    }
}
