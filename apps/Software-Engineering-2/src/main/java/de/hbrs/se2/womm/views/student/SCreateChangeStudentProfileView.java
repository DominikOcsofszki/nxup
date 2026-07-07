package de.hbrs.se2.womm.views.student;


import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.*;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import de.hbrs.se2.womm.config.SecurityService;
import de.hbrs.se2.womm.dtos.StudentDTO;
import de.hbrs.se2.womm.model.ProfilePictureB64;
import de.hbrs.se2.womm.services.StudentService;
import de.hbrs.se2.womm.services.UserDetailsManagerImpl;
import de.hbrs.se2.womm.views.LandingPageView;
import de.hbrs.se2.womm.views.layouts.AViewWomm;
import de.hbrs.se2.womm.views.layouts.ROUTING;
import de.hbrs.se2.womm.views.layouts.StudentLayout;
import jakarta.annotation.security.RolesAllowed;

import java.time.LocalDate;


@Route(value = ROUTING.STUDENT.SCreateChangeStudentProfileView, layout = StudentLayout.class)
@RolesAllowed({"STUDENT", "ADMIN"})
@PageTitle("CreateChangeStudentProfileView")
public class SCreateChangeStudentProfileView extends AViewWomm {
    private StudentService studentService;
    private StudentDTO studentDTO;
    private UserDetailsManagerImpl userDetailsManager;

    public SCreateChangeStudentProfileView(
            StudentService studentService,
            SecurityService securityService,
            UserDetailsManagerImpl userDetailsManager
    ) {
        this.userDetailsManager = userDetailsManager;
        this.studentService = studentService;
        this.studentDTO = studentService.getByNutzerId(securityService.getLoggedInNutzerID());
        add(
                getHeader(),
                getProfil()
        );

    }

    private void saveChanges(
            DatePicker geburtstag,
            Checkbox benachrichtigungen,
            EmailField email,
            EmailField confirmEmail,
            PasswordField oldPassword,
            PasswordField newPassword,
            PasswordField newPasswordConfirm,
            TextField ort,
            TextArea biographie,
            TextArea spezialisierungen,
            NumberField semester
    ) {
        if (validateDatepicker(geburtstag)) {
            studentDTO.setStudentGeburtstag(geburtstag.getValue().toString());
        }

        studentDTO.setStudentBenachrichtigung(benachrichtigungen.getValue());

        if (validateEmail(email, confirmEmail)) {
            studentDTO.getNutzer().setNutzerMail(email.getValue());
        }

        if (validatePassword(oldPassword, newPassword, newPasswordConfirm)) {
            userDetailsManager.changePassword(oldPassword.getValue(), newPassword.getValue());
        }

        if (!ort.getValue().isBlank()) {
            studentDTO.getNutzer().setNutzerOrt(ort.getValue());
        }

        studentDTO.setStudentBio(biographie.getValue());

        studentDTO.setStudentSpezialisierung(spezialisierungen.getValue());

        if (validateSemester(semester)) {
            studentDTO.setStudentSemester(semester.getValue().intValue());
        }
        studentService.saveStudent(studentDTO);
    }

    private Component getHeader() {
        HorizontalLayout header = new HorizontalLayout();
        header.setWidth("100%");
        return header;
    }

    private Component getProfil() {
        HorizontalLayout layoutProfile = new HorizontalLayout();
        VerticalLayout layoutPicture = new VerticalLayout();
        VerticalLayout layoutDetails = new VerticalLayout();

        Image imageProfilePicture = studentDTO.PlaceholderOrImage();
        imageProfilePicture.setWidth("250px");
        imageProfilePicture.setHeight("250px");

        ComboBox<ProfilePictureB64> comboBoxProfilePicture = new ComboBox<>();
        comboBoxProfilePicture.setItems(ProfilePictureB64.values());
        comboBoxProfilePicture.setItemLabelGenerator(ProfilePictureB64::name);
        comboBoxProfilePicture.setLabel("Profile Picture");
        comboBoxProfilePicture.addValueChangeListener(e -> {
            if (e.getValue() != null) {
                imageProfilePicture.setSrc(e.getValue().toString());
                studentDTO.getNutzer().setNutzerProfilbild(e.getValue().toString().getBytes());
            }
        });
        comboBoxProfilePicture.setWidth("250px");

        layoutPicture.add(
                imageProfilePicture,
                comboBoxProfilePicture
        );

        // ----------------- Header (Name) -----------------

        VerticalLayout layoutName = new VerticalLayout();
        Span spanName = getWommBuilder().Span.create("Name");
        Span spanNameValue = new Span(studentDTO.getStudentVorname() + " " + studentDTO.getStudentName());
        Span spanNameInformation = getWommBuilder().Span.create("The name can only be changed with the agreement of an admin");

        spanNameValue.getElement().getStyle()
                .set("font-size", "35px");
        spanNameInformation.getElement().getStyle()
                .set("color", "#5e5e5e");
        layoutName.setSpacing(false);
        layoutName.add(
                spanName,
                spanNameValue,
                spanNameInformation
        );

        // ----------------- Date of Birth -----------------

        VerticalLayout layoutDob = new VerticalLayout(); // Dob = Date of Birth
        Span spanDob = getWommBuilder().Span.create("Date of Birth");
        DatePicker datePickerDob = new DatePicker();
        DatePicker.DatePickerI18n singleFormatI18n = new DatePicker.DatePickerI18n();
        singleFormatI18n.setDateFormat("dd.MM.yyyy");
        datePickerDob.setI18n(singleFormatI18n);
        datePickerDob.setTooltipText(getWommBuilder().translateText("Select your date of birth"));
        datePickerDob.setErrorMessage(getWommBuilder().translateText("Invalid date given. Dates must follow the 'DD.MM.YYYY' format."));
        try {
            datePickerDob.setValue(LocalDate.parse(studentDTO.getStudentGeburtstag()));
        } catch (Exception e) {
            datePickerDob.setValue(LocalDate.now());
        }
        datePickerDob.setWidth("100%");
        layoutDob.setSpacing(false);
        layoutDob.add(
                spanDob,
                datePickerDob
        );

        // ----------------- E-Mail Checkbox -----------------

        Checkbox checkEmailNotifs = new Checkbox();

        Span spanEmailNotifsDescription = getWommBuilder().Span.create("I would like to receive notifications");
        spanEmailNotifsDescription.getElement().getStyle().set("font-size", "20px");

        checkEmailNotifs.setLabelComponent(spanEmailNotifsDescription);
        checkEmailNotifs.setValue(studentDTO.isStudentBenachrichtigung());

        // ----------------- E-Mail -----------------

        VerticalLayout layoutEmail = new VerticalLayout();
        Span emailFieldHeader = getWommBuilder().Span.create("e-mail");
        EmailField emailField = new EmailField();
        Span emailFieldConfirmHeader = getWommBuilder().Span.create("Confirm e-mail");
        EmailField emailFieldConfirm = new EmailField();

        emailFieldHeader.getElement().getStyle()
                .set("font-size", "20px");

        emailField.getStyle()
                .set("flex-grow", "1")
                .set("width", "100%");

        emailFieldConfirm.getStyle()
                .set("flex-grow", "1")
                .set("width", "100%");

        emailField.setValue(studentDTO.getNutzer().getNutzerMail() == null ? "" : studentDTO.getNutzer().getNutzerMail());
        emailField.setClearButtonVisible(true);
        emailField.setErrorMessage(getWommBuilder().translateText("Please enter a valid e-mail address"));
        emailField.setTooltipText(getWommBuilder().translateText("Please enter a valid e-mail address"));
        emailFieldConfirm.setValue("");
        emailFieldConfirm.setClearButtonVisible(true);
        emailFieldConfirm.setTooltipText(getWommBuilder().translateText("Please re-enter your valid e-mail address"));
        layoutEmail.setSpacing(false);
        layoutEmail.add(
                emailFieldHeader,
                emailField,
                emailFieldConfirmHeader,
                emailFieldConfirm
        );

        // ----------------- Password -----------------

        VerticalLayout layoutPassword = new VerticalLayout();
        Span spanPasswordFieldHeader = getWommBuilder().Span.create("Change Password");
        Span spanOldPassword = getWommBuilder().Span.create("Enter your old password");
        Span spanPasswordTooltip = getWommBuilder().Span.create("Your password must have 6 to 12 characters. Only letters A-Z and numbers supported.");
        PasswordField passwordOld = new PasswordField();
        Span spanNewPassword = getWommBuilder().Span.create("Enter your new password");
        PasswordField passwordNew = new PasswordField();
        Span spanNewPasswordConfirm = getWommBuilder().Span.create("Repeat your new password");
        PasswordField passwordNewConfirm = new PasswordField();

        spanPasswordFieldHeader.getElement().getStyle().set("font-size", "20px");
        spanOldPassword.getElement().getStyle().set("font-size", "15px");
        spanNewPassword.getElement().getStyle().set("font-size", "15px");
        spanNewPasswordConfirm.getElement().getStyle().set("font-size", "15px");

        passwordOld.setHelperComponent(spanPasswordTooltip);
        passwordOld.setClearButtonVisible(true);
        passwordOld.setTooltipText(getWommBuilder().translateText("Please enter your current valid Password"));
        passwordNew.setRequiredIndicatorVisible(true);
        passwordNew.setAllowedCharPattern("[A-Za-z0-9]");
        passwordNew.setMinLength(6);
        passwordNew.setMaxLength(12);
        passwordNew.setClearButtonVisible(true);
        passwordNew.setTooltipText(getWommBuilder().translateText("Please enter your new valid Password"));
        passwordNewConfirm.setRequiredIndicatorVisible(true);
        passwordNewConfirm.setAllowedCharPattern("[A-Za-z0-9]");
        passwordNewConfirm.setMinLength(6);
        passwordNewConfirm.setMaxLength(12);
        passwordNewConfirm.setClearButtonVisible(true);
        passwordNewConfirm.setTooltipText(getWommBuilder().translateText("Please re-enter your new valid Password"));

        passwordOld
                .getStyle()
                .set("flex-grow", "1")
                .set("width", "100%");
        passwordNew
                .getStyle()
                .set("flex-grow", "1")
                .set("width", "100%");
        passwordNewConfirm
                .getStyle()
                .set("flex-grow", "1")
                .set("width", "100%");
        layoutPassword.setSpacing(false);
        layoutPassword.add(
                spanPasswordFieldHeader,
                spanOldPassword,
                passwordOld,
                spanNewPassword,
                passwordNew,
                spanNewPasswordConfirm,
                passwordNewConfirm
        );

        // ----------------- Location -----------------

        TextField fieldLocation = new TextField();
        fieldLocation.setValue(studentDTO.getNutzer().getNutzerOrt() == null ? "" : studentDTO.getNutzer().getNutzerOrt());
        VerticalLayout layoutLocation = generateSinglePropertyField("Location", fieldLocation);
        layoutLocation.setSpacing(false);

        // ----------------- Bio -----------------

        TextArea textAreaBio = new TextArea();
        textAreaBio.setValue(studentDTO.getStudentBio() == null ? "" : studentDTO.getStudentBio());
        VerticalLayout layoutBio = generateSinglePropertyField("Biography", textAreaBio);
        layoutBio.setSpacing(false);
        layoutBio.getStyle().set("width", "200%");

        // ----------------- Specializations -----------------

        TextArea textAreaSpecializations = new TextArea();
        textAreaSpecializations.setValue(studentDTO.getStudentSpezialisierung() == null ? "" : studentDTO.getStudentSpezialisierung());
        VerticalLayout layoutSpec = generateSinglePropertyField("Specializations", textAreaSpecializations);
        layoutSpec.setSpacing(false);
        layoutSpec.getStyle().set("width", "200%");

        // ----------------- Semester -----------------

        NumberField numberFieldSemester = new NumberField();
        Integer semesters = studentDTO.getStudentSemester();
        numberFieldSemester.setValue(((semesters != null) ? Double.valueOf(semesters) : 0));
        VerticalLayout layoutSemester = generateSinglePropertyField(getWommBuilder().translateText("Semester"), numberFieldSemester);
        layoutSemester.setSpacing(false);


        // ----------------- Save Changes -----------------

        Button buttonSaveChanges = getWommBuilder().Button.create("Save Changes");
        buttonSaveChanges.addClickListener(e -> {
            saveChanges(
                    datePickerDob,
                    checkEmailNotifs,
                    emailField,
                    emailFieldConfirm,
                    passwordOld,
                    passwordNew,
                    passwordNewConfirm,
                    fieldLocation,
                    textAreaBio,
                    textAreaSpecializations,
                    numberFieldSemester
            );
            Notification notification = Notification
                    .show(getWommBuilder().translateText("Changes saved!"));
            notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        });

        layoutDetails.add(
                layoutName,
                new Hr(), layoutDob,
                new Hr(), checkEmailNotifs,
                new Hr(), layoutEmail,
                new Hr(), layoutPassword,
                new Hr(), layoutLocation,
                new Hr(), layoutBio,
                new Hr(), layoutSpec,
                new Hr(), layoutSemester,
                new Hr(), buttonSaveChanges
        );

        layoutProfile.add(
                layoutDetails,
                layoutPicture
        );

        return layoutProfile;
    }

    private VerticalLayout generateSinglePropertyField(String spanHeader, Component propertyField) {
        VerticalLayout layoutProperty = new VerticalLayout();
        Span spanPropertyHeader = getWommBuilder().Span.create(spanHeader);

        spanPropertyHeader.getElement()
                .getStyle()
                .set("font-size", "20px")
                .set("width", "100%");

        propertyField
                .getStyle()
                .set("width", "100%");
        layoutProperty.add(
                spanPropertyHeader,
                propertyField
        );


        return layoutProperty;
    }

    private boolean validateDatepicker(DatePicker geburtstag) {
        return (geburtstag != null &&
                geburtstag.getValue() != null &&
                !geburtstag.isInvalid() &&
                !geburtstag.toString().isBlank() &&
                geburtstag.getValue().isAfter(LocalDate.of(1900, 1, 1))
        );

    }

    private boolean validateEmail(EmailField email, EmailField confirmEmail) {
        return (email != null &&
                confirmEmail != null &&
                !email.isInvalid() &&
                !email.getValue().isBlank() &&
                email.getValue().equals(confirmEmail.getValue()));
    }

    private boolean validatePassword(PasswordField oldPassword, PasswordField newPassword, PasswordField newPasswordConfirm) {
        // Null guard clause
        if (oldPassword == null || newPassword == null || newPasswordConfirm == null) {
            return false;
        }

        // Empty / Invalid tests
        if (!newPassword.isInvalid() && newPassword.getValue().isBlank()) {
            return false;
        }

        // Passwords match
        if (!newPassword.getValue().equals(newPasswordConfirm.getValue())) {
            return false;
        }

        // Old password is not empty
        if (oldPassword.getValue().isBlank()) {
            return false;
        }

        return true;
    }

    private boolean validateSemester(NumberField semester) {
        return (semester != null &&
                semester.getValue() != null &&
                !semester.isInvalid() &&
                semester.getValue() > 0);
    }
}

