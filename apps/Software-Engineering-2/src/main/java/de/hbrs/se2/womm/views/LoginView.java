package de.hbrs.se2.womm.views;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import de.hbrs.se2.womm.config.SecurityService;
import de.hbrs.se2.womm.controller.AuthenticationController;
import de.hbrs.se2.womm.dtos.LoginRequest;
import de.hbrs.se2.womm.exceptions.AuthenticationException;
import de.hbrs.se2.womm.views.layouts.AViewWomm;
import de.hbrs.se2.womm.views.layouts.LoggedOutLayout;
import de.hbrs.se2.womm.views.layouts.ROUTING;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;


@Route(value = ROUTING.ALL.LoginView, layout = LoggedOutLayout.class)
@PermitAll
@PageTitle("LoginView")
public class LoginView extends AViewWomm {

    SecurityService securityService;

    LoginView(@Autowired AuthenticationController authenticationController, SecurityService securityService) {
        this.securityService = securityService;
        addClassName("login-view");
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        VerticalLayout loginFormLayout = new VerticalLayout();
        loginFormLayout.setAlignItems(Alignment.CENTER);
        loginFormLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        loginFormLayout.getStyle().setWidth("25%");
        loginFormLayout.setPadding(true);
        loginFormLayout.getStyle().setBackground("white");

        TextField usernameInput = getWommBuilder().TextField.create("Username");
        usernameInput.setWidthFull();
        usernameInput.setRequired(true);
        usernameInput.setRequiredIndicatorVisible(true);
        usernameInput.setErrorMessage(getWommBuilder().translateText("Username is required"));
        usernameInput.focus();


        PasswordField passwordInput = getWommBuilder().PasswordField.create("Enter Password");
        passwordInput.setWidthFull();
        passwordInput.setRequired(true);
        passwordInput.setRequiredIndicatorVisible(true);
        passwordInput.setErrorMessage(getWommBuilder().translateText("Password is required"));

        Button submitButton = getWommBuilder().Button.create("LogIn");
        submitButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        addClickListenerForEnter(submitButton, usernameInput, passwordInput);
        submitButton.addClickListener(e -> {
            LoginRequest loginRequest = LoginRequest.builder()
                    .username(usernameInput.getValue())
                    .password(passwordInput.getValue()).build();
            try {
                ResponseEntity<Authentication> response = authenticationController.loginUser(loginRequest);
                if (response.getStatusCode().is2xxSuccessful()) {
                    UI.getCurrent().getPage().setLocation("/");
                }
            } catch (AuthenticationException error) {
                Notification notification = new Notification();
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);

                Div text = new Div(new Text(error.getMessage()));

                Button closeButton = new Button(new Icon("lumo", "cross"));
                closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
                closeButton.setAriaLabel("Close");
                closeButton.addClickListener(event -> {
                    notification.close();
                });

                HorizontalLayout layout = new HorizontalLayout(text, closeButton);
                layout.setAlignItems(Alignment.CENTER);

                notification.add(layout);
                notification.open();
                notification.setDuration(1000); // TODO sec message is shown
            }
        });

        loginFormLayout.add(
                new H3(getWommBuilder().translateText("LogIn")), //TODO change I to i?
                usernameInput,
                passwordInput,
                submitButton
        );

        add(
                new H1("w.o.o.m."),
                loginFormLayout
        );
    }

    private void addClickListenerForEnter(Button submitButton, TextField textfield, PasswordField passwordfield) {
        textfield.setValueChangeMode(ValueChangeMode.EAGER);
                passwordfield.setValueChangeMode(ValueChangeMode.EAGER);
        UI.getCurrent().addShortcutListener(
                () -> submitButton.click(),
                Key.ENTER);
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
