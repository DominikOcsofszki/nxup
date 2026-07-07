package de.hbrs.se2.womm.views.unternehmen;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import de.hbrs.se2.womm.config.SecurityService;
import de.hbrs.se2.womm.dtos.StelleDTO;
import de.hbrs.se2.womm.dtos.UnternehmenDTO;
import de.hbrs.se2.womm.services.StelleService;
import de.hbrs.se2.womm.services.UnternehmenService;
import de.hbrs.se2.womm.views.components.GridFilterStelle;
import de.hbrs.se2.womm.views.extra.ASSETS;
import de.hbrs.se2.womm.views.layouts.AViewWomm;
import de.hbrs.se2.womm.views.layouts.ROUTING;
import de.hbrs.se2.womm.views.layouts.UnternehmenLayout;
import jakarta.annotation.security.RolesAllowed;

import java.util.List;

@Route(value = ROUTING.UNTERNEHMEN.UHomepageUnternehmenView, layout = UnternehmenLayout.class)
@RolesAllowed({"UNTERNEHMEN", "ADMIN"})
@PageTitle("HomepageUnternehmenView")
public class UHomepageUnternehmenView extends AViewWomm {
    UnternehmenDTO unternehmenDTO;
    private long aktuelleNutzerID;
    GridFilterStelle gridFilterStelle;
    public UHomepageUnternehmenView(UnternehmenService unternehmenService, SecurityService securityService, StelleService stelleService) {
        super();
        this.aktuelleNutzerID = securityService.getLoggedInNutzerID();
        this.unternehmenDTO = unternehmenService.getByNutzerId(aktuelleNutzerID);
        setUpHeader();

        setUpSearchFields();
        this.gridFilterStelle = new GridFilterStelle();
        List<StelleDTO> stelleDTOList = stelleService.getByNutzerId(aktuelleNutzerID);
        this.gridFilterStelle.setUpFromOutside(stelleDTOList);
        this.gridFilterStelle.setColumnClickListener(ROUTING.UNTERNEHMEN.UJobProjectWorkshopDisplayView);
        add(gridFilterStelle);

    }

    private void setUpComponentFilterGridControllerStellen() { //ToDo: this was added
    }


    private void setUpHeader() {
        VerticalLayout header = new VerticalLayout();
        String welcome = getWommBuilder().translateText("Welcome ");
        H1 headerText = new H1(welcome + unternehmenDTO.getName());
        H2 text = getWommBuilder().H2.create("Here you can see all your open advertisements");
        header.add(headerText,text);
        add(header);

    }

    private void setUpSearchFields() {
        setUpComponentFilterGridControllerStellen();
    }
}
