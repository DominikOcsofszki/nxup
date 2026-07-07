package de.hbrs.se2.womm.views.unternehmen;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import de.hbrs.se2.womm.config.SecurityService;
import de.hbrs.se2.womm.dtos.StelleDTO;
import de.hbrs.se2.womm.dtos.UnternehmenDTO;
import de.hbrs.se2.womm.model.ProfilePictureB64;
import de.hbrs.se2.womm.services.StelleService;
import de.hbrs.se2.womm.services.UnternehmenService;
import de.hbrs.se2.womm.views.components.GridFilterStelle;
import de.hbrs.se2.womm.views.layouts.AViewWomm;
import de.hbrs.se2.womm.views.layouts.ROUTING;
import de.hbrs.se2.womm.views.layouts.UnternehmenLayout;
import jakarta.annotation.security.RolesAllowed;

import java.util.List;

@Route(value = ROUTING.UNTERNEHMEN.UEditFirmProfileDisplayView, layout = UnternehmenLayout.class)
@RolesAllowed({"UNTERNEHMEN", "ADMIN"})
@PageTitle("EditFirmProfileDisplayView")
public class UEditFirmProfileDisplayView extends AViewWomm {


    UnternehmenDTO unternehmenDTO;
    TextArea descriptionTextArea = new TextArea(getWommBuilder().translateText("Company Description"));
    TextArea gruendung = new TextArea(getWommBuilder().translateText("Since"));
    TextField locationField = getWommBuilder().TextField.create("Company Location");
    TextField websiteField = getWommBuilder().TextField.create("Company Website");

    private long aktuelleNutzerID;
    private UnternehmenService unternehmenService;
    GridFilterStelle gridFilterStelle;


    protected UEditFirmProfileDisplayView(UnternehmenService unternehmenService, StelleService stelleService, SecurityService securityService) {
        super();
        this.aktuelleNutzerID = securityService.getLoggedInNutzerID();
        this.unternehmenDTO = unternehmenService.getByNutzerId(aktuelleNutzerID);
        this.unternehmenService = unternehmenService;
        setUp();
        this.gridFilterStelle = new GridFilterStelle();
        List<StelleDTO> stelleDTOList = stelleService.getByNutzerId(aktuelleNutzerID);
        this.gridFilterStelle.setUpFromOutside(stelleDTOList);
    }

    private void setUp() {
        // Name
        H2 firmName = new H2(unternehmenDTO.getName());
        String companyDescription = unternehmenDTO.getBeschreibung() == null ?
                "Company Description" : unternehmenDTO.getBeschreibung();

        // Logo
        Image imageProfilePicture = unternehmenDTO.PlaceholderOrImage();
        imageProfilePicture.setWidth("250px");
        imageProfilePicture.setHeight("250px");
        // Location
        String companyLocation = unternehmenDTO.getNutzer().getNutzerOrt() == null ?
                "Company Location" : unternehmenDTO.getNutzer().getNutzerOrt();
        // Website
        String companyWebsite = unternehmenDTO.getWebsite_url() == null ?
                "Company Website" : unternehmenDTO.getWebsite_url();

        //Set up Fields:
        descriptionTextArea.setValue(companyDescription);
        String gruendung1 = unternehmenDTO.getGruendung() == null ?
                "Company Since" : unternehmenDTO.getGruendung();
        gruendung.setValue(gruendung1);

        // Logo, Company Name, and Edit Firm Profile Button
        HorizontalLayout buttonsLayout = new HorizontalLayout();
        HorizontalLayout logoAndEditLayout = new HorizontalLayout();
        VerticalLayout logoAndName = new VerticalLayout();

        logoAndName.add(firmName);
        logoAndName.add(imageProfilePicture/*,comboBoxProfilePicture*/);
        logoAndEditLayout.add(logoAndName);

        //Save Button
        Button saveButton = getWommBuilder().Button.create("Save Changes");
        saveButton.addClickListener(e -> {
            unternehmenService.saveUnternehmen(newUnternehmenDTOFromFields());
            unternehmenService.saveUnternehmen(unternehmenDTO);
            UI.getCurrent().getPage().reload();
        });

        descriptionTextArea.setWidth("100%");
        descriptionTextArea.setHeight("200px");
        add(buttonsLayout);
        buttonsLayout.add(logoAndEditLayout);
        buttonsLayout.add(saveButton);

        locationField.setValue(companyLocation);
        websiteField.setValue(companyWebsite);

        add(locationField, websiteField);
        add(descriptionTextArea, gruendung);
    }

    private UnternehmenDTO newUnternehmenDTOFromFields() {
        unternehmenDTO.setBeschreibung(descriptionTextArea.getValue());
        unternehmenDTO.setGruendung(gruendung.getValue());
        unternehmenDTO.getNutzer().setNutzerOrt(locationField.getValue());
        unternehmenDTO.setWebsite_url(websiteField.getValue());
        return unternehmenDTO;
    }
}
