package de.hbrs.se2.womm.views.sonardupplicates;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.OptionalParameter;
import de.hbrs.se2.womm.config.SecurityService;
import de.hbrs.se2.womm.dtos.*;
import de.hbrs.se2.womm.model.ApplicationStatus;
import de.hbrs.se2.womm.services.BenachrichtigungService;
import de.hbrs.se2.womm.services.BewerbungService;
import de.hbrs.se2.womm.services.StelleService;
import de.hbrs.se2.womm.services.StudentService;
import de.hbrs.se2.womm.views.components.Stellenanzeige;
import de.hbrs.se2.womm.views.layouts.AViewWomm;
import de.hbrs.se2.womm.views.layouts.ROUTING;

import java.util.Date;
import java.util.Optional;

public abstract class BJobProjectWorkshopDisplayView extends AViewWomm implements HasUrlParameter<Long> {
    SecurityService securityService;
    StelleService stelleService;
    BewerbungService bewerbungService;
    StudentService studentService;
    StelleDTO stelleDTO;
    protected long stelleId;
    VerticalLayout applicationForm;
    boolean formToggle = false;
    BenachrichtigungService benachrichtigungService;

    public BJobProjectWorkshopDisplayView(StelleService stelleService,
                                          SecurityService securityService,
                                          BewerbungService bewerbungService,
                                          StudentService studentService,
                                          BenachrichtigungService benachrichtigungService) {

        this.benachrichtigungService = benachrichtigungService;
        this.securityService = securityService;
        this.bewerbungService = bewerbungService;
        this.studentService = studentService;
        this.stelleService = stelleService;
        this.applicationForm = new VerticalLayout();
    }

    @Override
    public void setParameter(BeforeEvent event, @OptionalParameter Long parameter) {
        if (parameter != null) {
            this.stelleId = parameter;
            Optional<StelleDTO> checkStelleDTO = stelleService.getById(this.stelleId);
            if (checkStelleDTO.isEmpty()) {
                add(new H1("Keine Stelle in der DB für ID: " + this.stelleId + " gefunden"));
            } else {
                this.stelleDTO = checkStelleDTO.get();
                setUpHeader();
                setupAnzeige();
                if(securityService.isUserStudent()) {
                    setUpButtons();
                }
            }

        } else {
            setup404Page();
        }

    }

    private void setUpHeader() {
        HorizontalLayout header = new HorizontalLayout();

        //Ueberschrift
        String unternehmenName = this.stelleDTO.getUnternehmen().getName(); //ToDo Changed
        H1 name = new H1(unternehmenName);
        if(securityService.isUserStudent()) {
            String routingToUnternehmenWebsite = ROUTING.STUDENT.SFirmProfileDisplayView;
            name.addClickListener(e -> UI.getCurrent()
                    .navigate(routingToUnternehmenWebsite+ "/" + stelleDTO.getUnternehmen().getUnternehmenId()));
            name.getStyle().set("cursor", "pointer");
        }
        header.add(name);
        add(header);
    }

    private void setupAnzeige() {
        Stellenanzeige stellenanzeige = new Stellenanzeige();
        VerticalLayout anzeigeLayout = stellenanzeige.fillOutLayout(stelleDTO);
        add(anzeigeLayout);
    }

    private void setUpButtons() {
        HorizontalLayout buttons = new HorizontalLayout();

        //Erstellen-Button
        Button bewerbungButton = new Button(getWommBuilder().translateText("Apply now"), new Icon(VaadinIcon.PENCIL));
        bewerbungButton.addClickListener(e -> {
            if (formToggle) {
                applicationForm.removeAll();
                remove(applicationForm);
                bewerbungButton.setText(getWommBuilder().translateText("Apply now"));
                bewerbungButton.setIcon(new Icon(VaadinIcon.PENCIL));
                setFormToggle(!formToggle);
            } else {
                displayApplicationForm();
                bewerbungButton.setText(getWommBuilder().translateText("Apply later"));
                bewerbungButton.setIcon(new Icon(VaadinIcon.ANGLE_UP));
                setFormToggle(!formToggle);
            }
        });
        buttons.add(bewerbungButton);

        add(buttons);
    }

    private void displayApplicationForm() {
        applicationForm.setPadding(false);

        TextArea textArea = new TextArea();
        textArea.setPlaceholder(getWommBuilder().translateText("Write a short application here"));
        textArea.setWidthFull();
        textArea.setMinHeight("200px");

        applicationForm.add(textArea);

        //Erstellen-Button
        Button erstellenButton = new Button(getWommBuilder().translateText("Send"));
        erstellenButton.addClickListener(e -> {
            if (textArea.getValue().trim().isEmpty())
                createErrorNotification(getWommBuilder().translateText("Please enter your application into the text field!"));
            else if (stelleService.getById(stelleId).isPresent()) {
                StelleDTO currentStelle = stelleService.getById(stelleId).get();
                long studentId = securityService.getLoggedInNutzerID();
                StudentDTO currentUser = studentService.getByNutzerId(studentId);
                UnternehmenDTO unternehmen = currentStelle.getUnternehmen();
                var returned = bewerbungService.saveBewerbung(BewerbungDTO.builder()
                        .bewerbungStelle(currentStelle)
                        .bewerbungStudent(currentUser)
                        .bewerbungUnternehmen(unternehmen)
                        .bewerbungStatus(ApplicationStatus.AUSSTEHEND.toString())
                        .bewerbungText(textArea.getValue()).build());
                if (returned != null) {
                    BenachrichtigungDTO benachrichtigungDTO = BenachrichtigungDTO.builder()
                            .nutzer(unternehmen.getNutzer())
                            .nachricht(getWommBuilder().translateText("You have a new application from ") + currentUser.getStudentName())
                            .gelesen(false)
                            .date(new Date())
                            .build();
                    benachrichtigungService.saveBenachrichtigung(benachrichtigungDTO);
                    createSuccessNotification();
                }
            } else {
                createErrorNotification(getWommBuilder().translateText("The job offer is not available anymore :("));
            }
        });

        applicationForm.add(erstellenButton);

        add(applicationForm);
    }

    private void setFormToggle(boolean formToggle) {
        this.formToggle = formToggle;
    }

    private void createErrorNotification(String errorText) {
        Notification notification = new Notification();
        notification.addThemeVariants(NotificationVariant.LUMO_ERROR);

        Div text = new Div(new Text(errorText));

        Button closeButton = new Button(new Icon("lumo", "cross"));
        closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
        closeButton.addClickListener(e -> notification.close());

        HorizontalLayout layout = new HorizontalLayout(text, closeButton);
        layout.setAlignItems(Alignment.CENTER);

        notification.add(layout);
        notification.open();
    }

    private void createSuccessNotification() {
        Notification notification = new Notification();
        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);

        Div text = new Div(getWommBuilder().Text.create(getWommBuilder().translateText("Your application has been sent successfully")));

        Button closeButton = new Button(new Icon("lumo", "cross"));
        closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
        closeButton.addClickListener(e -> {
            notification.close();
            UI.getCurrent().navigate(ROUTING.STUDENT.SHomepageStudentView);
        });

        HorizontalLayout layout = new HorizontalLayout(text, closeButton);
        layout.setAlignItems(Alignment.CENTER);

        notification.add(layout);
        notification.open();
    }

    private void setup404Page() {
        add(new H1("404 Not Found! :("));
    }
}
