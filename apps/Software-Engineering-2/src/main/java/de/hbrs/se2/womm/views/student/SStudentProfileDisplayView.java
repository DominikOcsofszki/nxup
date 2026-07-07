package de.hbrs.se2.womm.views.student;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import de.hbrs.se2.womm.config.SecurityService;
import de.hbrs.se2.womm.dtos.StudentDTO;
import de.hbrs.se2.womm.services.StudentService;
import de.hbrs.se2.womm.views.layouts.AViewWomm;
import de.hbrs.se2.womm.views.layouts.ROUTING;
import de.hbrs.se2.womm.views.layouts.StudentLayout;
import jakarta.annotation.security.RolesAllowed;

@Route(value = ROUTING.STUDENT.SStudentProfileDisplayView, layout = StudentLayout.class)
@RolesAllowed({"STUDENT", "ADMIN"})
@PageTitle("StudentProfileDisplayView")
public class SStudentProfileDisplayView extends AViewWomm {
    StudentDTO studentDTO;

    public SStudentProfileDisplayView(StudentService studentService, SecurityService securityService) {
        super();
        this.studentDTO = studentService.getByNutzerId(securityService.getLoggedInNutzerID());

        add(
                getHeader(),
                getProfil()
        );
    }

    private Component getHeader() {
        HorizontalLayout header = new HorizontalLayout();
        header.setWidth("100%");
        return header;
    }

    /**
     * Generiert die Profilseite
     *
     * @return Die Profilseite
     */
    private Component getProfil() {
        HorizontalLayout profilPage = new HorizontalLayout();
        VerticalLayout profilDetails = new VerticalLayout();
        VerticalLayout profilBild = new VerticalLayout();

        Image bild = studentDTO.PlaceholderOrImage();
        bild.setWidth("300px");
        bild.setHeight("300px");
        bild.getStyle().set("margin-left", "auto"); // Sodass das Bild rechtsbündig ist

        profilBild.add(bild);

        VerticalLayout rowName = generateProfileDetailsRow(
                "Name", studentDTO.getStudentVorname() + " " + studentDTO.getStudentName(), "45"
        );
        VerticalLayout rowGeburtstag = generateProfileDetailsRow(
                "Date of Birth", studentDTO.getStudentGeburtstag(), "20"
        );
        VerticalLayout rowEmail = generateProfileDetailsRow(
                "E-Mail Address", studentDTO.getNutzer().getNutzerMail(),"20"
        );
        VerticalLayout rowLocation = generateProfileDetailsRow(
                "Location", studentDTO.getNutzer().getNutzerOrt(),"20"
        );
        VerticalLayout rowBiography = generateProfileDetailsRow(
                "Biography", studentDTO.getStudentBio(),"20"
        );
        VerticalLayout rowSpec = generateProfileDetailsRow(
                "Specializations", studentDTO.getStudentSpezialisierung(),"20"
        );
        VerticalLayout rowSemester = generateProfileDetailsRow(
                "Semester", String.valueOf(studentDTO.getStudentSemester()),"20"
        );

        profilDetails.add(
                rowName,
                new Hr(), rowGeburtstag,
                new Hr(), rowEmail,
                new Hr(), rowLocation,
                new Hr(), rowBiography,
                new Hr(), rowSpec,
                new Hr(), rowSemester
        );

        profilPage.setWidth("50%");

        profilPage.add(profilDetails);
        profilPage.add(profilBild);
        return profilPage;
    }

    /**
     * Generiert eine Zeile für die Profilseite
     *
     * @param _header  Die Überschrift der Zeile
     * @param _content Der Inhalt der Zeile
     * @return Die fertige Zeile
     */
    private VerticalLayout generateProfileDetailsRow(String _header, String _content, String fontsize) {
        VerticalLayout row = new VerticalLayout();

        Span header = getWommBuilder().Span.create(_header);
        Span content = new Span(_content);

        header.getElement().getStyle().set("font-size", "20px");
        content.getElement().getStyle().set("font-size", fontsize+"px");

        row.add(header);
        row.setSpacing(false);
        row.setPadding(false);
        row.add(content);

        return row;
    }
}
