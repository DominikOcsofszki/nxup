package de.hbrs.se2.womm.views.student;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import de.hbrs.se2.womm.config.SecurityService;
import de.hbrs.se2.womm.dtos.AboDTO;
import de.hbrs.se2.womm.dtos.StelleDTO;
import de.hbrs.se2.womm.dtos.UnternehmenDTO;
import de.hbrs.se2.womm.entities.Stelle;
import de.hbrs.se2.womm.services.AboStudentUnternehmenService;
import de.hbrs.se2.womm.services.StelleService;
import de.hbrs.se2.womm.services.StudentService;
import de.hbrs.se2.womm.services.UnternehmenService;
import de.hbrs.se2.womm.views.components.GridFilterStelle;
import de.hbrs.se2.womm.views.extra.ASSETS;
import de.hbrs.se2.womm.views.layouts.AViewWomm;
import de.hbrs.se2.womm.views.layouts.ROUTING;
import de.hbrs.se2.womm.views.layouts.StudentLayout;
import jakarta.annotation.security.RolesAllowed;

import java.util.ArrayList;
import java.util.List;

@Route(value = ROUTING.STUDENT.SFirmProfileDisplayView, layout = StudentLayout.class)
@RolesAllowed({"STUDENT", "ADMIN"})
@PageTitle("FirmProfileDisplayView")
public class SFirmProfileDisplayView extends AViewWomm implements HasUrlParameter<Long> {

    private UnternehmenService unternehmenService;
    private StelleService stelleService;
    private UnternehmenDTO unternehmenDTO;
    private Long unternehmenID;
    private Long studentID;
    GridFilterStelle gridFilterStelle;
    SecurityService securityService;
    StudentService studentService;
    AboStudentUnternehmenService aboStudentUnternehmenService;

    public SFirmProfileDisplayView(UnternehmenService unternehmenService, StelleService stelleService,
                                   SecurityService securityService, StudentService studentService,
                                   AboStudentUnternehmenService aboStudentUnternehmenService) {
        this.unternehmenService = unternehmenService;
        this.stelleService = stelleService;
        this.securityService = securityService;
        this.studentService = studentService;
        this.studentID = studentService.getByNutzerId(securityService.getLoggedInNutzerID()).getStudentId();
        this.aboStudentUnternehmenService = aboStudentUnternehmenService;
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, Long parameter) {
        this.unternehmenID = parameter;

        this.unternehmenDTO = unternehmenService.getUnternehmenPerID(unternehmenID);

        setUp();
        this.gridFilterStelle = new GridFilterStelle();
        List<StelleDTO> stelleDTOList = stelleService.getByUnternehmenId(unternehmenID);
        this.gridFilterStelle.setUpFromOutside(stelleDTOList);
        gridFilterStelle.setColumnClickListener(ROUTING.STUDENT.SJobProjectWorkshopDisplayView);
        add(gridFilterStelle);
    }

    private AboDTO returnAboDTO() {
        AboDTO AboDTO = de.hbrs.se2.womm.dtos.AboDTO.builder().build();
        AboDTO.setAboBenachrichtigungen(true);
        AboDTO.setStudent(studentService.getById(studentID).get());
        AboDTO.setUnternehmen(unternehmenService.getUnternehmenPerID(unternehmenID));
        return AboDTO;
    }

    boolean setUpCheckSubscrition() {
        try { //TODO check again
            aboStudentUnternehmenService.saveAboStudentUnternehmen(returnAboDTO());
        } catch (Exception e) {
            Notification.show("You are already subscribed to this company");
            return false;
        }
        return true;
    }

    private void setUp() {
        // Logo, Company Name, Subscribe and Chat Button
        HorizontalLayout buttonsLayout = new HorizontalLayout();

        // Logo, Company Name, Subscribe and Chat Buttons
        HorizontalLayout logoAndSubscribeLayout = new HorizontalLayout();
        Div logoAndName = new Div();
//            Image companyLogo = new Image(ASSETS.IMG.IMG9, "Firmen Logo Hier");
        Image companyLogo = new Image(ASSETS.IMG.PLACEHOLDER, "placeholder");
        companyLogo.setWidth(200 + "px");
        companyLogo.setHeight(200 + "px");
        logoAndName.add(companyLogo);
        logoAndName.add(new H2(unternehmenDTO.getName())); // Replace with the actual company name
        logoAndSubscribeLayout.add(logoAndName);

        // Subscribe Button
        Button subscribeButton = getWommBuilder().Button.create("Subscribe");
        subscribeButton.addClickListener(e -> {
            // Logic for subscription
            boolean notSubscribedYet =  setUpCheckSubscrition();
            if (notSubscribedYet)Notification.show("Subscribed!");
        });
        logoAndSubscribeLayout.add(subscribeButton);

        // Chat Button
        Button chatButton = getWommBuilder().Button.create("Chat");
        chatButton.addClickListener(e -> {
            // Logic for opening a chat
            Notification.show("Opening Chat...");
        });

        add(buttonsLayout);
        buttonsLayout.add(logoAndSubscribeLayout);
        buttonsLayout.add(chatButton);

        // Company Location, Number of Employees, and Company Website
        HorizontalLayout detailsLayout = new HorizontalLayout();

        // Company Location with Geo Tag Icon
        HorizontalLayout locationLayout = new HorizontalLayout();
        locationLayout.add(new Icon(VaadinIcon.MAP_MARKER), new Span(unternehmenDTO.getNutzer().getNutzerOrt())); // Replace with the actual location
        detailsLayout.add(locationLayout);

        // Link to Company Website with Icon
        HorizontalLayout websiteLayout = new HorizontalLayout();
        Icon linkIcon = new Icon(VaadinIcon.EXTERNAL_LINK);

        String website;
        if(!this.unternehmenDTO.getWebsite_url().isEmpty()) {
        if(this.unternehmenDTO.getWebsite_url().substring(0,4).equalsIgnoreCase("http")){
            website = this.unternehmenDTO.getWebsite_url();
        }else{
            website = "https://" + this.unternehmenDTO.getWebsite_url();
        }
        }
        else{
            website = "";
        }
        Anchor anchor = new Anchor(website,this.unternehmenDTO.getWebsite_url());
        anchor.getStyle().setColor("#0000EE");
        websiteLayout.add(linkIcon, anchor);
        detailsLayout.add(websiteLayout);

        add(detailsLayout);
        Paragraph companyDescription = new Paragraph(unternehmenDTO.getBeschreibung());
        add(companyDescription);
    }

    // Dummy-Stellenanzeigen erstellen (nur für Testzwecke) //TODO DELETE?
    private List<Stelle> createDummyStellenanzeigen() {
        List<Stelle> dummyStellenanzeigen = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            Stelle stelle = new Stelle();
            stelle.setStelleId(i);
            stelle.setStelleTitel("Job " + i);
            stelle.setStelleTitel("Description " + i);
            stelle.setStelleOrt("Location " + i);
            dummyStellenanzeigen.add(stelle);
        }
        return dummyStellenanzeigen;
    }

}
