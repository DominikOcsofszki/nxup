package de.hbrs.se2.womm.views;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import de.hbrs.se2.womm.config.SecurityService;
import de.hbrs.se2.womm.controller.AuthenticationController;
import de.hbrs.se2.womm.dtos.StudentRegistrationRequest;
import de.hbrs.se2.womm.views.layouts.AViewWomm;
import de.hbrs.se2.womm.views.layouts.LoggedOutLayout;
import de.hbrs.se2.womm.views.layouts.ROUTING;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Route(value = ROUTING.ALL.RegistrierungStudentView, layout = LoggedOutLayout.class)
@AnonymousAllowed
@PageTitle("RegistrierungStudentView")
public class RegistrierungStudentView extends AViewWomm {

    SecurityService securityService;
    AuthenticationController authenticationController;

    private static final String EMAIL_REGEX =
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    TextField nameComponent;
    TextField surnameComponent;
    TextField usernameComponent;
    EmailField emailComponent;
    PasswordField passwordComponent;
    PasswordField passwordConfirmComponent;
    DatePicker dateOfBirthComponent;
    TextField locationComponent;
    Button registerComponent;
    ProgressBar progressBar = new ProgressBar();

    public RegistrierungStudentView(AuthenticationController authenticationController, SecurityService securityService) {
        super();

        this.securityService = securityService;
        this.authenticationController = authenticationController;

        setSizeFull();

        setAlignItems(Alignment.CENTER);

        setJustifyContentMode(JustifyContentMode.CENTER);

        H4 h4 = getWommBuilder().H4.create("Student Registration");
        add(h4);

        nameComponent = getWommBuilder().TextField.create("Name");
        String yourRealName = getWommBuilder().translateText("Your REAL Name");
        nameComponent.setTooltipText(yourRealName);
        nameComponent.setRequired(true);
        nameComponent.setRequiredIndicatorVisible(true);
        String nameIsRequired = getWommBuilder().translateText("Name is required");
        nameComponent.setErrorMessage(nameIsRequired);

        surnameComponent = getWommBuilder().TextField.create("Surname");
        String yourRealSurname = getWommBuilder().translateText("Your REAL surname");
        surnameComponent.setTooltipText(yourRealSurname);
        surnameComponent.setClearButtonVisible(true);
        surnameComponent.setRequired(true);
        surnameComponent.setRequiredIndicatorVisible(true);
        String surnameIsRequired = getWommBuilder().translateText("Surname is required");
        surnameComponent.setErrorMessage(surnameIsRequired);

        usernameComponent = getWommBuilder().TextField.create("Username");
        String yourDesiredUsername = getWommBuilder().translateText("Your desired username");
        usernameComponent.setTooltipText(yourDesiredUsername);
        usernameComponent.setRequired(true);
        usernameComponent.setRequiredIndicatorVisible(true);
        String usernameIsRequired = getWommBuilder().translateText("Username is required");
        usernameComponent.setErrorMessage(usernameIsRequired);

        emailComponent = getWommBuilder().EmailField.create("Email");
        String text = getWommBuilder().translateText("Email connected to your future account");
        emailComponent.setTooltipText(text);
        emailComponent.setRequired(true);
        emailComponent.setRequiredIndicatorVisible(true);
        emailComponent.setPrefixComponent(VaadinIcon.ENVELOPE.create());
        String emailIsRequired = getWommBuilder().translateText("Email is required");
        emailComponent.setErrorMessage(emailIsRequired);

        passwordComponent = getWommBuilder().PasswordField.create("Enter Password");
        String thePasswordUsedForLogin = getWommBuilder().translateText("Your password must have 6 to 12 characters. Only letters A-Z and numbers supported");
        passwordComponent.setTooltipText(thePasswordUsedForLogin);
        passwordComponent.setRequired(true);
        passwordComponent.setAllowedCharPattern("[A-Za-z0-9]");
        passwordComponent.setMinLength(6);
        passwordComponent.setMaxLength(12);
        passwordComponent.setRequiredIndicatorVisible(true);
        String passwordIsRequired = getWommBuilder().translateText("Your password must have 6 to 12 characters. Only letters A-Z and numbers supported");
        passwordComponent.setErrorMessage(passwordIsRequired);

        passwordConfirmComponent = getWommBuilder().PasswordField.create("Confirm Password");
        String repeatYourPassword = getWommBuilder().translateText("Your password must have 6 to 12 characters. Only letters A-Z and numbers supported");
        passwordConfirmComponent.setTooltipText(repeatYourPassword);
        passwordConfirmComponent.setRequired(true);
        passwordConfirmComponent.setAllowedCharPattern("[A-Za-z0-9]");
        passwordConfirmComponent.setMinLength(6);
        passwordConfirmComponent.setMaxLength(12);
        passwordConfirmComponent.setRequiredIndicatorVisible(true);
        String passwordConfirmationIsRequired = getWommBuilder().translateText("Your password must have 6 to 12 characters. Only letters A-Z and numbers supported");
        passwordConfirmComponent.setErrorMessage(passwordConfirmationIsRequired);

        DatePicker.DatePickerI18n singleFormatI18n = new DatePicker.DatePickerI18n();
        singleFormatI18n.setDateFormat("dd.MM.yyyy");
        dateOfBirthComponent = getWommBuilder().DatePicker.create("Date of Birth");
        dateOfBirthComponent.setI18n(singleFormatI18n);
        String selectYourDateOfBirth = getWommBuilder().translateText("Select your date of birth");
        dateOfBirthComponent.setTooltipText(selectYourDateOfBirth);
        dateOfBirthComponent.setRequired(true);
        dateOfBirthComponent.setRequiredIndicatorVisible(true);
        String errorMessage = getWommBuilder().translateText("Invalid date given. Dates must follow the 'DD.MM.YYYY' format.");
        dateOfBirthComponent.setErrorMessage(errorMessage);

        LocalDate today = LocalDate.now();

        dateOfBirthComponent.setValue(today);

        locationComponent = getWommBuilder().TextField.create("Location");
        String yourCurrentLivingLocation = getWommBuilder().translateText("Your current living location");
        locationComponent.setTooltipText(yourCurrentLivingLocation);
        locationComponent.setRequired(true);
        locationComponent.setRequiredIndicatorVisible(true);
        locationComponent.setPrefixComponent(VaadinIcon.PIN.create());
        String locationIsRequired = getWommBuilder().translateText("Location is required");
        locationComponent.setErrorMessage(locationIsRequired);


        registerComponent = getWommBuilder().Button.create("Register", event -> {
            if (!nameComponent.isEmpty() && !surnameComponent.isEmpty() &&
                    !usernameComponent.isEmpty() && !emailComponent.isEmpty() &&
                    !passwordComponent.isEmpty() && !passwordConfirmComponent.isEmpty() &&
                    !passwordComponent.isInvalid() && !passwordConfirmComponent.isInvalid() &&
                    !locationComponent.isEmpty()) {
                if (passwordComponent.getValue().equals(passwordConfirmComponent.getValue())) {
                    Pattern pattern = Pattern.compile(EMAIL_REGEX);
                    Matcher matcher = pattern.matcher(emailComponent.getValue());
                    if (matcher.matches()) {
                        setUpLoadingBarRegistration();
                        register();
                    } else {
                        createErrorNotification(getWommBuilder().translateText("Please enter a valid email!"));
                    }
                } else {
                    createErrorNotification(getWommBuilder().translateText("Passwords not identical!"));
                }
            } else {
                createErrorNotification(getWommBuilder().translateText("Fill in all fields!"));
            }
        });

        FormLayout formLayout = new FormLayout();

        formLayout.add(
                nameComponent,
                surnameComponent,
                usernameComponent,
                emailComponent,
                passwordComponent,
                passwordConfirmComponent,
                dateOfBirthComponent,
                locationComponent
        );

        add(
                formLayout,
                registerComponent
        );
    }

    private void setUpLoadingBarRegistration() {
        progressBar.setIndeterminate(true);
        add(progressBar);
    }

    private void register() {
        try {
            StudentRegistrationRequest request = getStudentRegistrationRequest();

            ResponseEntity<Void> response = authenticationController.registerStudent(request);

            if (response.getStatusCode().is2xxSuccessful()) {
                Notification notification = new Notification();
                notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);

                Div text = new Div(getWommBuilder().Text.create("Registration succesful!"));

                Button closeButton = new Button(new Icon("lumo", "cross"));
                closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
                createClickListener(notification, text, closeButton);
                UI.getCurrent().navigate(ROUTING.ALL.LoginView);
            }
        } catch (Exception e) {
            createErrorNotification(e.getMessage());
        }
    }

    static void createClickListener(Notification notification, Div text, Button closeButton) {
        closeButton.addClickListener(e -> {
            notification.close();
            UI.getCurrent().navigate(ROUTING.ALL.LoginView);
        });

        HorizontalLayout layout = new HorizontalLayout(text, closeButton);
        layout.setAlignItems(Alignment.CENTER);

        notification.add(layout);
        notification.open();
    }

    private StudentRegistrationRequest getStudentRegistrationRequest() {
        StudentRegistrationRequest request = new StudentRegistrationRequest();
        request.setFirstname(nameComponent.getValue());
        request.setLastname(surnameComponent.getValue());
        request.setEmail(emailComponent.getValue());
        request.setUsername(usernameComponent.getValue());
        request.setPassword(passwordComponent.getValue());

        request.setDob(dateOfBirthComponent.getValue().toString());

        request.setLocation(locationComponent.getValue());
        return request;
    }

    private void createErrorNotification(String errorText) {
        Notification notification = new Notification();
        notification.addThemeVariants(NotificationVariant.LUMO_ERROR);

        Div text = new Div(new Text(errorText));

        Button closeButton = new Button(new Icon("lumo", "cross"));
        closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
        closeButton.addClickListener(e -> {
            notification.close();
            this.remove(progressBar);
        });

        HorizontalLayout layout = new HorizontalLayout(text, closeButton);
        layout.setAlignItems(Alignment.CENTER);

        notification.add(layout);
        notification.open();
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        if (securityService.getAuthenticatedUser() != null) {
            if (securityService.isUserStudent()) UI.getCurrent().navigate(ROUTING.STUDENT.SHomepageStudentView);
            if (securityService.isUserUnternehmen())
                UI.getCurrent().navigate(ROUTING.UNTERNEHMEN.UHomepageUnternehmenView);
        }
    }
}
