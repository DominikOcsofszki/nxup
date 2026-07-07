package de.hbrs.se2.womm.views.unternehmen;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import de.hbrs.se2.womm.config.SecurityService;
import de.hbrs.se2.womm.dtos.BewerbungDTO;
import de.hbrs.se2.womm.dtos.StudentDTO;
import de.hbrs.se2.womm.dtos.UnternehmenDTO;
import de.hbrs.se2.womm.model.ApplicationStatus;
import de.hbrs.se2.womm.services.BewerbungService;
import de.hbrs.se2.womm.services.StudentService;
import de.hbrs.se2.womm.services.UnternehmenService;
import de.hbrs.se2.womm.views.layouts.AViewWomm;
import de.hbrs.se2.womm.views.layouts.ROUTING;
import de.hbrs.se2.womm.views.layouts.UnternehmenLayout;
import de.hbrs.se2.womm.views.utils.TextToParagraphFormatter;
import jakarta.annotation.security.RolesAllowed;

import java.util.Optional;

@Route(value = ROUTING.UNTERNEHMEN.UApplicationView, layout = UnternehmenLayout.class)
@RolesAllowed({"UNTERNEHMEN", "ADMIN"})
@PageTitle("ApplicationView")
public class UApplicationView extends AViewWomm implements HasUrlParameter<Long> {

    private final SecurityService securityService;
    private final BewerbungService bewerbungService;
    private final StudentService studentService;
    private final UnternehmenService unternehmenService;
    private BewerbungDTO bewerbung;
    private String bewerbungText;
    private StudentDTO student;
    private UnternehmenDTO unternehmen;
    private Long studentID;
    private String studentName;
    private String studentVorname;

    public UApplicationView(BewerbungService bewerbungService,
                            StudentService studentService,
                            SecurityService securityService,
                            UnternehmenService unternehmenService) {
        super();
        this.bewerbungService = bewerbungService;
        this.studentService = studentService;
        this.securityService = securityService;
        this.unternehmenService = unternehmenService;
    }


    @Override
    public void setParameter(BeforeEvent beforeEvent, @OptionalParameter Long bewerbungID) {
        if (bewerbungID != null) {
            Optional<BewerbungDTO> fetchedBewerbung = bewerbungService.getById(bewerbungID);
            fetchedBewerbung.ifPresent(bewerbungDTO -> this.bewerbung = bewerbungDTO);
            setUpApplication();
        } else {
            setup404Page();
        }
    }

    void setUpApplication() {
        bewerbungText = bewerbung.getBewerbungText();

        studentID = bewerbung.getBewerbungStudent().getStudentId();
        Optional<StudentDTO> fetchedStudent = studentService.getById(studentID);
        fetchedStudent.ifPresent(studentDTO -> student = studentDTO);
        studentName = student.getStudentName();
        studentVorname = student.getStudentVorname();

        long userId = securityService.getLoggedInNutzerID();
        unternehmen = unternehmenService.getByNutzerId(userId);

        setUpTop();
        setUpAnschreiben();

        String status = bewerbung.getBewerbungStatus();

        if (status.equals(ApplicationStatus.AUSSTEHEND.toString())) setupButtons();
        else if (status.equals(ApplicationStatus.AKZEPTIERT.toString())) createConfirmation(true);
        else if (status.equals(ApplicationStatus.ABGELEHNT.toString())) createConfirmation(false);
    }

    private void setUpTop() {
        HorizontalLayout top = new HorizontalLayout();
        top.setAlignItems(FlexComponent.Alignment.CENTER);
        top.setJustifyContentMode(JustifyContentMode.AROUND);
        Image image = student.PlaceholderOrImage();
        image.setWidth(200, Unit.PIXELS);
        image.setHeight(200, Unit.PIXELS);
        top.add(image);
        H3 name = new H3(String.format("%s, %s", studentName, studentVorname));
        name.getStyle().set("cursor", "pointer");
        name.addClickListener(e -> UI.getCurrent().navigate(ROUTING.UNTERNEHMEN.UApplicantView + "/" + studentID));
        top.add(name);
        add(top);
    }

    private void setUpAnschreiben() {
        VerticalLayout anschreiben = TextToParagraphFormatter.formatTextToParagraph(bewerbungText);
        add(anschreiben);
    }

    private void setupButtons() {
        HorizontalLayout buttons = new HorizontalLayout();
        Button acceptButton = new Button(getWommBuilder().translateText("Accept"));
        acceptButton.setIcon(new Icon(VaadinIcon.CHECK));
        acceptButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        acceptButton.addClickListener(e -> {
            BewerbungDTO updatedBewerbung = BewerbungDTO.builder()
                    .bewerbungStatus(ApplicationStatus.AKZEPTIERT.toString())
                    .bewerbungStelle(bewerbung.getBewerbungStelle())
                    .bewerbungStudent(student)
                    .bewerbungUnternehmen(unternehmen)
                    .bewerbungText(bewerbungText)
                    .bewerbungId(bewerbung.getBewerbungId()).build();
            bewerbungService.saveBewerbung(updatedBewerbung);
            remove(buttons);
            createConfirmation(true);
        });

        Button declineButton = new Button(getWommBuilder().translateText("Decline"));
        declineButton.setIcon(new Icon(VaadinIcon.CLOSE));
        declineButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        declineButton.addClickListener(e -> {
            BewerbungDTO updatedBewerbung = BewerbungDTO.builder()
                    .bewerbungStatus(ApplicationStatus.ABGELEHNT.toString())
                    .bewerbungStelle(bewerbung.getBewerbungStelle())
                    .bewerbungStudent(student)
                    .bewerbungUnternehmen(unternehmen)
                    .bewerbungText(bewerbungText)
                    .bewerbungId(bewerbung.getBewerbungId()).build();
            bewerbungService.saveBewerbung(updatedBewerbung);
            remove(buttons);
            createConfirmation(false);
        });

        buttons.add(acceptButton);
        buttons.add(declineButton);

        add(buttons);
    }

    private void createConfirmation(boolean accepted) {
        HorizontalLayout confirmation = new HorizontalLayout();
        H3 text;
        if (accepted) {
            Icon icon = new Icon(VaadinIcon.CHECK_CIRCLE);
            icon.setColor("green");
            confirmation.add(icon);
            text = new H3(getWommBuilder().translateText("You have accepted the application"));
            text.getStyle().setColor("green");
            confirmation.add(text);
        } else {
            Icon icon = new Icon(VaadinIcon.CLOSE_CIRCLE);
            icon.setColor("red");
            confirmation.add(icon);
            text = new H3(getWommBuilder().translateText("You have declined the application"));
            text.getStyle().setColor("red");
            confirmation.add(text);
        }
        add(confirmation);
    }

    private void setup404Page() {
        add(new H1("404 Not Found! :("));
    }
}
