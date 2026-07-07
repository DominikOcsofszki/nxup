package de.hbrs.se2.womm.views.unternehmen;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;
import de.hbrs.se2.womm.config.SecurityService;
import de.hbrs.se2.womm.dtos.AboDTO;
import de.hbrs.se2.womm.dtos.BenachrichtigungDTO;
import de.hbrs.se2.womm.dtos.StelleDTO;
import de.hbrs.se2.womm.dtos.UnternehmenDTO;
import de.hbrs.se2.womm.services.AboStudentUnternehmenService;
import de.hbrs.se2.womm.services.BenachrichtigungService;
import de.hbrs.se2.womm.services.StelleService;
import de.hbrs.se2.womm.services.UnternehmenService;
import de.hbrs.se2.womm.views.extra.VaadinBuilderWomm;
import de.hbrs.se2.womm.views.layouts.AViewWomm;
import de.hbrs.se2.womm.views.layouts.ROUTING;
import de.hbrs.se2.womm.views.layouts.UnternehmenLayout;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

@Route(value = ROUTING.UNTERNEHMEN.UStelleAnzeigeErstellenView, layout = UnternehmenLayout.class)
@RolesAllowed({"UNTERNEHMEN", "ADMIN"})
@PageTitle("StelleAnzeigeErstellenView")
public class UStelleAnzeigeErstellenView extends AViewWomm
        implements HasUrlParameter<String> {

    TextField stelleTitel = new TextField();
    TextField stelleOrt = new TextField();
    TextField stelleWebsite = new TextField();
    TextArea stelleBeschreibung = new TextArea();
    Select<String> stellenanzeigenTyp;
    String chosenTyp;

    @Autowired
    BenachrichtigungService benachrichtigungService;
    StelleService stelleService;
    UnternehmenService unternehmenService;
    SecurityService securityService;
    AboStudentUnternehmenService aboStudentUnternehmenService;
    String valueFromQuerry;
    private long aktuelleNutzerID;
    private UnternehmenDTO unternehmenDTO;
    private StelleDTO stelleToEdit;
    private int stellePrimaryKey;

    private static final String URL_REGEX = "(http://www.)[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]|(https://www.)[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]|(www.)[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";

    @Override
    public void setParameter(BeforeEvent event, @OptionalParameter String parameter) {
        if (parameter == null) {
            valueFromQuerry = "";
        } else {
            add(String.format("parameter: %s.",
                    parameter));
            valueFromQuerry = parameter;
        }
    }

    public UStelleAnzeigeErstellenView(StelleService stelleService,
                                       UnternehmenService unternehmenService,
                                       SecurityService securityService,
                                       AboStudentUnternehmenService aboStudentUnternehmenService) {
        super();
        this.stelleToEdit = null;
        this.aktuelleNutzerID = securityService.getLoggedInNutzerID();
        this.unternehmenDTO = unternehmenService.getByNutzerId(aktuelleNutzerID);
        this.stelleService = stelleService;
        this.unternehmenService = unternehmenService;
        this.securityService = securityService;
        this.aboStudentUnternehmenService = aboStudentUnternehmenService;
        this.chosenTyp = "Werkstudenten-Stelle";
        setUpHeader();
        setUpStellenanzeige();
    }

    private void setUpHeader() {
        HorizontalLayout header = new HorizontalLayout();
        //Ueberschrift
        header.add(getWommBuilder().H1.create("Create advertisement"));

        add(header);
    }

    private void setUpStellenanzeige() {
        VerticalLayout stellenanzeige = new VerticalLayout();

        //Selector
        String type = getWommBuilder().translateText("Advertisement type");
        this.stellenanzeigenTyp = new Select<>(type, this::selectComponentListener);
        stellenanzeigenTyp.setWidth("min-content");
        String projekt = getWommBuilder().translateText("Project");
        String werkst = getWommBuilder().translateText("Student job");
        stellenanzeigenTyp.setItems("Workshop", projekt, werkst);
        stellenanzeigenTyp.setValue(werkst);

        stellenanzeige.add(stellenanzeigenTyp);

        //TextfeldTitel
        String stellenb = getWommBuilder().translateText("Ad description");
        stelleTitel.setPlaceholder(stellenb);
        stelleTitel.setClearButtonVisible(true);
        stelleTitel.setRequired(true);
        stelleTitel.setErrorMessage(getWommBuilder().translateText("A title must be provided"));

        stellenanzeige.add(stelleTitel);

        //Textfeld StelleOrt
        String ort = getWommBuilder().translateText("Location");
        stelleOrt.setPlaceholder(ort);
        stelleOrt.setClearButtonVisible(true);
        stelleOrt.setRequired(true);
        stelleOrt.setErrorMessage(getWommBuilder().translateText("A location must be provided"));

        stellenanzeige.add(stelleOrt);

        //Textfeld StelleWebsite
        stelleWebsite.setPlaceholder("URL");
        stelleWebsite.setClearButtonVisible(true);
        stelleWebsite.setRequired(true);

        stelleWebsite.setPattern(URL_REGEX);
        stelleWebsite.setRequiredIndicatorVisible(true);
        stelleWebsite.setErrorMessage(getWommBuilder().translateText("A valid url must be provided"));

        stellenanzeige.add(stelleWebsite);

        //Textfeld StelleBeschreibung
        stelleBeschreibung.setWidthFull();
        String beschreibung = getWommBuilder().translateText("Description");
        stelleBeschreibung.setPlaceholder(beschreibung);
        stelleBeschreibung.setClearButtonVisible(true);
        stelleBeschreibung.setRequired(true);
        stelleBeschreibung.setErrorMessage(getWommBuilder().translateText("A description must be provided"));
        stelleWebsite.setRequiredIndicatorVisible(true);

        stellenanzeige.add(stelleBeschreibung);

        //Erstellen-Button
        Button erstellenButton = getWommBuilder().Button.create("Create");
        erstellenButton.addClickListener(e -> {
            if (stelleWebsite.getValue().matches(URL_REGEX)
                    && !stelleOrt.getValue().trim().isEmpty()
                    && !stelleTitel.getValue().trim().isEmpty()
                    && !stelleBeschreibung.getValue().trim().isEmpty()) {
                buildAndSaveStelleDTO();
            } else {
                Notification notification = new Notification();
                notification.setText(getWommBuilder().translateText("Please check your Inputs! All fields must be entered correctly!"));
                notification.open();
                notification.setDuration(6000);
            }

        });

        stellenanzeige.add(erstellenButton);

        add(stellenanzeige);

    }

    protected void setUpFieldForEdit(StelleDTO stelleDTO) {
                stelleToEdit = stelleDTO;
                stellePrimaryKey = stelleDTO.getStelleId().intValue();
                stelleTitel.setValue(stelleDTO.getStelleTitel());
                stelleOrt.setValue(stelleDTO.getStelleOrt());
                stelleWebsite.setValue(stelleDTO.getStelleWebsite());
                stelleBeschreibung.setValue(stelleDTO.getStelleBeschreibung());
                unternehmenDTO = stelleDTO.getUnternehmen();
    }
    private void buildAndSaveStelleDTO() {
        StelleDTO erzeugDTO = StelleDTO.builder()
                .stelleId(stelleToEdit == null ? null : stelleToEdit.getStelleId())
                .stelleTyp(chosenTyp)
                .stelleTitel(stelleTitel.getValue())
                .stelleOrt(stelleOrt.getValue())
                .stelleWebsite(stelleWebsite.getValue())
                .stelleBeschreibung(stelleBeschreibung.getValue())
                .unternehmen(unternehmenDTO)
                .erstellungsdatum((new Date()))
                .build();
        stelleService.saveStelle(erzeugDTO);
        List<AboDTO> allAboDTO =
                aboStudentUnternehmenService.getByUnternehmenId(unternehmenDTO.getUnternehmenId());

String msg  = "Neue Stelle: " + erzeugDTO.getStelleTitel() + "\n" + "Ort: " + erzeugDTO.getStelleOrt() +
        "\n" + "Beschreibung: " + erzeugDTO.getStelleBeschreibung() + "\n" + "Website: " +
        erzeugDTO.getStelleWebsite();
        int countAbo = allAboDTO.size();
        allAboDTO.forEach(
                AboDTO -> {
                    if (AboDTO.getAboBenachrichtigungen()) {
                        BenachrichtigungDTO msgDTO = BenachrichtigungDTO.builder()
                                .nachricht(msg)
                                .gelesen(false)
                                .date(new Date())
                                .nutzer(AboDTO.getStudent().getNutzer())
                                .build();

                        benachrichtigungService.saveBenachrichtigung(msgDTO);
                    }
                });
        Notification notification = new Notification();
        notification.setText(VaadinBuilderWomm.translateTextStatic("Abo-count: ") + countAbo);
        notification.open();
        notification.setDuration(5000);

        getUI().ifPresent(ui -> ui.navigate(ROUTING.UNTERNEHMEN.UHomepageUnternehmenView));
    }

    private void selectComponentListener(AbstractField.ComponentValueChangeEvent<Select<String>, String> e) {
        this.chosenTyp = e.getValue();
    }

}
