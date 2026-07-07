package de.hbrs.se2.womm.views;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import de.hbrs.se2.womm.config.SecurityService;
import de.hbrs.se2.womm.controller.AuthenticationController;
import de.hbrs.se2.womm.dtos.CompanyRegistrationRequest;
import de.hbrs.se2.womm.views.layouts.AViewWomm;
import de.hbrs.se2.womm.views.layouts.LoggedOutLayout;
import de.hbrs.se2.womm.views.layouts.ROUTING;
import org.springframework.http.ResponseEntity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Route(value = ROUTING.ALL.RegistrierungUnternehmenView, layout = LoggedOutLayout.class)
@AnonymousAllowed
@PageTitle("RegistrierungUnternehmenView")
public class RegistrierungUnternehmenView extends AViewWomm {
    SecurityService securityService;
    AuthenticationController authenticationController;

    private static final String EMAIL_REGEX =
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    TextField companyNameComponent;
    TextField usernameComponent;
    EmailField emailComponent;
    PasswordField passwordComponent;
    PasswordField passwordConfirmComponent;
    TextField locationComponent;
    Button registerComponent;

    public RegistrierungUnternehmenView(AuthenticationController authenticationController, SecurityService securityService) {
        super();

        this.securityService = securityService;
        this.authenticationController = authenticationController;

        setSizeFull();

        setAlignItems(FlexComponent.Alignment.CENTER);

        setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

        H4 h4 = getWommBuilder().H4.create("Company Registration");
        add(h4);

        companyNameComponent = getWommBuilder().TextField.create("Company Name");
        String yourRealName = getWommBuilder().translateText("Your REAL Company Name");
        companyNameComponent.setTooltipText(yourRealName);
        companyNameComponent.setRequired(true);
        companyNameComponent.setRequiredIndicatorVisible(true);
        String nameIsRequired = getWommBuilder().translateText("Company Name is required");
        companyNameComponent.setErrorMessage(nameIsRequired);

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

        locationComponent = getWommBuilder().TextField.create("Location");
        String yourCurrentLivingLocation = getWommBuilder().translateText("Your current living location");
        locationComponent.setTooltipText(yourCurrentLivingLocation);
        locationComponent.setRequired(true);
        locationComponent.setRequiredIndicatorVisible(true);
        locationComponent.setPrefixComponent(VaadinIcon.PIN.create());
        String locationIsRequired = getWommBuilder().translateText("Location is required");
        locationComponent.setErrorMessage(locationIsRequired);


        registerComponent = getWommBuilder().Button.create("Register", event -> {
            if (!companyNameComponent.isEmpty()  && !usernameComponent.isEmpty() && !emailComponent.isEmpty() &&
                    !passwordComponent.isInvalid() && !passwordConfirmComponent.isInvalid() &&
                    !passwordComponent.isEmpty() && !passwordConfirmComponent.isEmpty() && !locationComponent.isEmpty()) {
                if (passwordComponent.getValue().equals(passwordConfirmComponent.getValue())) {
                    Pattern pattern = Pattern.compile(EMAIL_REGEX);
                    Matcher matcher = pattern.matcher(emailComponent.getValue());
                    if (matcher.matches()) {
                        register();
                    } else {
                        createErrorNotification(getWommBuilder().translateText("Please enter a valid email!"));
                    }
                }
                else {
                    createErrorNotification(getWommBuilder().translateText("Passwords not identical!"));
                }
            } else {
                createErrorNotification(getWommBuilder().translateText("Fill in all fields!"));
            }
        });

        FormLayout formLayout = new FormLayout();

        formLayout.add(
                companyNameComponent,
                usernameComponent,
                emailComponent,
                passwordComponent,
                passwordConfirmComponent,
                locationComponent
        );

        add(
                formLayout,
                registerComponent
        );
    }

    private void register() {
        try {
            CompanyRegistrationRequest request = getCompanyRegistrationRequest();

            ResponseEntity<Void> response = authenticationController.registerCompany(request);

            if(response.getStatusCode().is2xxSuccessful()) {
                Notification notification = new Notification();
                notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);

                Div text = new Div(getWommBuilder().Text.create("Registration succesful!"));

                Button closeButton = new Button(new Icon("lumo", "cross"));
                closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
                String close = getWommBuilder().translateText("Close");
                closeButton.setAriaLabel(close);
                RegistrierungStudentView.createClickListener(notification, text, closeButton);
                UI.getCurrent().navigate(ROUTING.ALL.LoginView);

            }
        } catch (Exception e) {
            createErrorNotification(e.getMessage());
        }
    }

    private CompanyRegistrationRequest getCompanyRegistrationRequest() {
        CompanyRegistrationRequest request = new CompanyRegistrationRequest();
        request.setName(companyNameComponent.getValue());
        request.setEmail(emailComponent.getValue());
        request.setUsername(usernameComponent.getValue());
        request.setPassword(passwordComponent.getValue());
        request.setLocation(locationComponent.getValue());
        return request;
    }

    private void createErrorNotification(String errorText) {
        Notification notification = new Notification();
        notification.addThemeVariants(NotificationVariant.LUMO_ERROR);

        Div text = new Div(new Text(errorText));

        Button closeButton = new Button(new Icon("lumo", "cross"));
        closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
        String close = getWommBuilder().translateText("Close");
        closeButton.setAriaLabel(close);
        closeButton.addClickListener(e -> notification.close());

        HorizontalLayout layout = new HorizontalLayout(text, closeButton);
        layout.setAlignItems(FlexComponent.Alignment.CENTER);

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
