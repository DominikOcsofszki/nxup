package de.hbrs.se2.womm.views.unternehmen;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import de.hbrs.se2.womm.config.SecurityService;
import de.hbrs.se2.womm.dtos.StelleDTO;
import de.hbrs.se2.womm.dtos.UnternehmenDTO;
import de.hbrs.se2.womm.services.StelleService;
import de.hbrs.se2.womm.services.UnternehmenService;
import de.hbrs.se2.womm.views.components.GridFilterStelle;
import de.hbrs.se2.womm.views.layouts.AViewWomm;
import de.hbrs.se2.womm.views.layouts.ROUTING;
import de.hbrs.se2.womm.views.layouts.UnternehmenLayout;
import jakarta.annotation.security.RolesAllowed;

import java.util.List;

@Route(value = ROUTING.UNTERNEHMEN.UFirmProfileDisplayView, layout = UnternehmenLayout.class)
@RolesAllowed({"UNTERNEHMEN", "ADMIN"})
@PageTitle("FirmProfileDisplayView")
public class UFirmProfileDisplayView extends AViewWomm {
    private final UnternehmenDTO unternehmenDTO;
    private long aktuelleNutzerID;
    GridFilterStelle gridFilterStelle;

    public UFirmProfileDisplayView(UnternehmenService unternehmenService, StelleService stelleService, SecurityService securityService) {
        super();
        this.aktuelleNutzerID = securityService.getLoggedInNutzerID();
        this.unternehmenDTO = unternehmenService.getByNutzerId(aktuelleNutzerID);
        setUp();
        this.gridFilterStelle = new GridFilterStelle();
        List<StelleDTO> stelleDTOList = stelleService.getByNutzerId(aktuelleNutzerID);
        this.gridFilterStelle.setUpFromOutside(stelleDTOList);
        gridFilterStelle.setColumnClickListener(ROUTING.UNTERNEHMEN.UJobProjectWorkshopDisplayView);
        add(gridFilterStelle);
    }

    private void setUp() {
        // Logo, Company Name, Subscribe and Chat Button
        HorizontalLayout buttonsLayout = new HorizontalLayout();

        // Logo, Company Name, Subscribe and Chat Buttons
        HorizontalLayout logoAndSubscribeLayout = new HorizontalLayout();
        Div logoAndName = new Div();
        Image companyLogo = unternehmenDTO.PlaceholderOrImage();
        companyLogo.setWidth(200 + "px");
        companyLogo.setHeight(200 + "px");

        logoAndName.add(companyLogo);
        logoAndName.add(new H2(unternehmenDTO.getName())); // Replace with the actual company name
        logoAndSubscribeLayout.add(logoAndName);

        // Edit Button
        Button editButton = getWommBuilder().Button.create("Edit firm profile");
        editButton.addClickListener(e -> {
            UI.getCurrent().navigate(UEditFirmProfileDisplayView.class);
        });

        add(buttonsLayout);
        buttonsLayout.add(logoAndSubscribeLayout);
        buttonsLayout.add(editButton);


        // Company Location, Number of Employees, and Company Website
        HorizontalLayout detailsLayout = new HorizontalLayout();

        // Company Location with Geo Tag Icon
        HorizontalLayout locationLayout = new HorizontalLayout();
        locationLayout.add(new Icon(VaadinIcon.MAP_MARKER), new Span(unternehmenDTO.getNutzer().getNutzerOrt())); // Replace with the actual location
        detailsLayout.add(locationLayout);

        // Link to Company Website with Icon
        if (unternehmenDTO.getWebsite_url() != null) {
            HorizontalLayout websiteLayout = new HorizontalLayout();
            Icon linkIcon = new Icon(VaadinIcon.EXTERNAL_LINK);

            String website;
            if(!unternehmenDTO.getWebsite_url().isEmpty()) {
                if (this.unternehmenDTO.getWebsite_url().substring(0, 4).equalsIgnoreCase("http")) {
                    website = this.unternehmenDTO.getWebsite_url();
                } else {
                    website = "https://" + this.unternehmenDTO.getWebsite_url();
                }
            } else {
                website = "";
            }
            Anchor anchor = new Anchor(website,this.unternehmenDTO.getWebsite_url());
            anchor.getStyle().setColor("#0000EE");
            websiteLayout.add(linkIcon, anchor);
            detailsLayout.add(websiteLayout);
        }

        add(detailsLayout);
        Paragraph companyDescription = new Paragraph(unternehmenDTO.getBeschreibung());
        add(companyDescription);
    }
}
