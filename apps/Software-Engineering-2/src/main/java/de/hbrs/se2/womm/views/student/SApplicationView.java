package de.hbrs.se2.womm.views.student;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import de.hbrs.se2.womm.dtos.BewerbungDTO;
import de.hbrs.se2.womm.dtos.StelleDTO;
import de.hbrs.se2.womm.model.ApplicationStatus;
import de.hbrs.se2.womm.services.BewerbungService;
import de.hbrs.se2.womm.services.ImageService;
import de.hbrs.se2.womm.services.StelleService;
import de.hbrs.se2.womm.views.components.Stellenanzeige;
import de.hbrs.se2.womm.views.layouts.AViewWomm;
import de.hbrs.se2.womm.views.layouts.ROUTING;
import de.hbrs.se2.womm.views.layouts.StudentLayout;
import de.hbrs.se2.womm.views.utils.TextToParagraphFormatter;
import jakarta.annotation.security.RolesAllowed;

import java.util.Optional;

@Route(value = ROUTING.STUDENT.SApplicationView, layout = StudentLayout.class)
@RolesAllowed({"ADMIN", "STUDENT"})
@PageTitle("ApplicationView")
public class SApplicationView extends AViewWomm implements HasUrlParameter<Long> {
    private final BewerbungService bewerbungService;
    private final StelleService stelleService;
    private BewerbungDTO bewerbung;
    private String bewerbungText;
    private StelleDTO stelleDTO;

    public SApplicationView(BewerbungService bewerbungService, StelleService stelleService) {
        super();
        this.bewerbungService = bewerbungService;
        this.stelleService = stelleService;
    }


    @Override
    public void setParameter(BeforeEvent beforeEvent, @OptionalParameter Long bewerbungID) {
        if (bewerbungID != null) {
            Optional<BewerbungDTO> fetchedBewerbung = bewerbungService.getById(bewerbungID);
            fetchedBewerbung.ifPresent(bewerbungDTO -> this.bewerbung = bewerbungDTO);
            Optional<StelleDTO> checkStelleDTO = stelleService.getById(bewerbung.getBewerbungStelle().getStelleId());
            this.stelleDTO = checkStelleDTO.orElse(null);


            setUpApplication();
        } else {
            setup404Page();
        }
    }

    void setUpApplication() {
        bewerbungText = bewerbung.getBewerbungText();

        setUpTop();

        String status = bewerbung.getBewerbungStatus();
        setUpStatus(status);

        setUpAnschreiben();

    }

    private void setUpTop() {
        setUpHeader();
        setupAnzeige();
    }

    private void setUpAnschreiben() {
        add(new H3(getWommBuilder().translateText("Your motivational letter:")));
        VerticalLayout anschreiben = TextToParagraphFormatter.formatTextToParagraph(bewerbungText);
        add(anschreiben);
    }

    private void setUpStatus(String bewerbungStatus) {
        HorizontalLayout layout = new HorizontalLayout();
        H3 text;
        if (ApplicationStatus.AKZEPTIERT.toString().equals(bewerbungStatus)) {
            Icon icon = new Icon(VaadinIcon.CHECK_CIRCLE);
            icon.setColor("green");
            layout.add(icon);
            text = new H3(getWommBuilder().translateText("Your application has been accepted."));
            text.getStyle().setColor("green");
            layout.add(text);
        } else if (ApplicationStatus.ABGELEHNT.toString().equals(bewerbungStatus)) {
            Icon icon = new Icon(VaadinIcon.CLOSE_CIRCLE);
            icon.setColor("red");
            layout.add(icon);
            text = new H3(getWommBuilder().translateText("Your application has been delcined."));
            text.getStyle().setColor("red");
            layout.add(text);
        } else if (ApplicationStatus.AUSSTEHEND.toString().equals(bewerbungStatus)) {
            Icon icon = new Icon(VaadinIcon.HOURGLASS_START);
            icon.setColor("orange");
            layout.add(icon);
            text = new H3(getWommBuilder().translateText("Your application is awaiting review."));
            text.getStyle().setColor("orange");
            layout.add(text);
        }
        add(layout);
    }

    private void setUpHeader() {
        HorizontalLayout header = new HorizontalLayout();
        ImageService imageService = new ImageService();
        Image i = imageService.getRandomImageHeight(100);
        header.add(i);
        String unternehmenName = this.stelleDTO.getUnternehmen().getName();
        H1 name = new H1(unternehmenName);
        name.addClickListener(e -> UI.getCurrent()
                .navigate(ROUTING.STUDENT.SFirmProfileDisplayView + "/" + stelleDTO.getUnternehmen().getUnternehmenId()));
        name.getStyle().set("cursor", "pointer");
        header.add(name);
        add(header);
    }

    private void setupAnzeige() {
        Stellenanzeige stellenanzeige = new Stellenanzeige();
        VerticalLayout anzeigeLayout = stellenanzeige.fillOutLayout(stelleDTO);
        add(anzeigeLayout);
    }

    private void setup404Page() {
        add(new H1("404 Not Found! :("));
    }
}
