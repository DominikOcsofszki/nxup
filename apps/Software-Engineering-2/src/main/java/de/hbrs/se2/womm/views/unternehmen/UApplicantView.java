package de.hbrs.se2.womm.views.unternehmen;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import de.hbrs.se2.womm.dtos.StudentDTO;
import de.hbrs.se2.womm.services.StudentService;
import de.hbrs.se2.womm.views.layouts.AViewWomm;
import de.hbrs.se2.womm.views.layouts.ROUTING;
import de.hbrs.se2.womm.views.layouts.UnternehmenLayout;
import jakarta.annotation.security.RolesAllowed;

import java.util.Optional;

@Route(value = ROUTING.UNTERNEHMEN.UApplicantView, layout = UnternehmenLayout.class)
@RolesAllowed({"UNTERNEHMEN", "ADMIN"})
@PageTitle("ApplicantView")
public class UApplicantView extends AViewWomm implements HasUrlParameter<Long> {

    StudentDTO student;
    StudentService studentService;

    public UApplicantView(StudentService studentService) {
        this.studentService = studentService;
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, Long studentId) {
        if (studentId != null) {
            Optional<StudentDTO> fetchedStudent = studentService.getById(studentId);
            fetchedStudent.ifPresent(studentDTO -> this.student = studentDTO);
            setupStudentProfile();
        } else {
            setup404Page();
        }
    }

    private void setup404Page() {
        add(new H1("404 Not Found! :("));
    }

    private void setupStudentProfile() {
        createNameAndProfileImageRow();

        VerticalLayout emailRow = createProfileRow(
                getWommBuilder().translateText("E-Mail Address"),
                student.getNutzer().getNutzerMail()
        );

        VerticalLayout birthdayRow = createProfileRow(
                getWommBuilder().translateText("Date of Birth"),
                student.getStudentGeburtstag()
        );

        VerticalLayout locationRow = createProfileRow(
                getWommBuilder().translateText("Location"),
                student.getNutzer().getNutzerOrt()
        );

        VerticalLayout bioRow = createProfileRow(
                getWommBuilder().translateText("Biography"),
                student.getStudentBio()
        );

        VerticalLayout spezialisierungRow = createProfileRow(
                getWommBuilder().translateText("Specializations"),
                student.getStudentSpezialisierung()
        );

        VerticalLayout semesterRow = createProfileRow(
                getWommBuilder().translateText("Semester"),
                student.getStudentSemester().toString()
        );

        add(
                emailRow, new Hr(),
                birthdayRow, new Hr(),
                locationRow, new Hr(),
                bioRow, new Hr(),
                spezialisierungRow, new Hr(),
                semesterRow
        );
    }

    private void createNameAndProfileImageRow() {
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setAlignItems(Alignment.END);
        horizontalLayout.getStyle().set("margin-bottom", "20px");

        H1 name = new H1(student.getStudentName() + ", " + student.getStudentVorname());
        Image image = student.PlaceholderOrImage();

        horizontalLayout.add(
                image, name,
                new Hr()
        );

        add(horizontalLayout);
    }



    private VerticalLayout createProfileRow(String description, String content) {
        VerticalLayout row = new VerticalLayout();

        Span header = new Span(description);
        Span text = new Span(content);

        header.getElement().getStyle().set("font-size", "20px");
        text.getElement().getStyle().set("font-size", "14px");

        row.add(header);
        row.setSpacing(false);
        row.setPadding(false);
        row.add(content);

        return row;
    }}
