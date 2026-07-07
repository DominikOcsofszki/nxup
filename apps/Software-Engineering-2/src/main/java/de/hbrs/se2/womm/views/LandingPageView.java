package de.hbrs.se2.womm.views;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import de.hbrs.se2.womm.views.layouts.AViewWomm;
import de.hbrs.se2.womm.views.layouts.LoggedOutLayout;
import de.hbrs.se2.womm.views.layouts.ROUTING;
import jakarta.annotation.security.RolesAllowed;

@Route(value = ROUTING.ALL.LandingPageView, layout = LoggedOutLayout.class)
@RolesAllowed({"UNTERNEHMEN", "ADMIN", "STUDENT"})
@AnonymousAllowed
@PageTitle("LandingPageView")
public class LandingPageView extends AViewWomm {

    public LandingPageView() {
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        // Header-Bereich
        HorizontalLayout header = createHeader();


        // Hero-Bereich
        VerticalLayout heroSection = createHeroSection();
        add(heroSection);

        // Logo-Bereich
        HorizontalLayout logoSection = createLogoSection();
        add(logoSection);

        // Beschreibung
        VerticalLayout descriptionSection = createDescriptionSection();
        add(descriptionSection);

        // Registrierungs- oder Anmeldebereich
        HorizontalLayout registrationSection = createRegistrationSection();
        add(registrationSection);

        // Kontaktinformationen
        Div contactInfo = createContactInfo();
        add(contactInfo);

        // Footer-Bereich
        HorizontalLayout footer = createFooter();
        add(footer);
    }

    private void listenEnterForLoginClick(Button buttonLog) {
        UI.getCurrent().addShortcutListener(
                () -> buttonLog.click(),
                Key.ENTER);
    }
    private HorizontalLayout createHeader() {
        HorizontalLayout header = new HorizontalLayout();
        Image logoImage = new Image("themes/theme_1/Womm_big_logo.png", "");
        logoImage.setWidth("280px");
        logoImage.setHeight("60px");
        header.add(logoImage);
        return header;
    }


    private HorizontalLayout createLogoSection() {
        HorizontalLayout logoSection = new HorizontalLayout();
               return logoSection;
    }
    private VerticalLayout createHeroSection() {
        VerticalLayout heroSection = new VerticalLayout();
        heroSection.setAlignItems(Alignment.CENTER);
        Image heroImage = new Image("themes/theme_1/Hiring_pic.jpg", "");
        heroImage.setWidth("500px");
        heroImage.setHeight("400px");
        heroSection.add(heroImage);
        return heroSection;
    }


    private VerticalLayout createDescriptionSection() {
        VerticalLayout descriptionSection = new VerticalLayout();
        descriptionSection.setAlignItems(Alignment.CENTER);
        H1 title = getWommBuilder().H1.create("Find your dream job on w.o.m.m.");
        title.getStyle().set("color", "#044FA3"); // HEX-Farbcode
        descriptionSection.add(title);

        Paragraph description = getWommBuilder().Paragraph.create("Your job search portal. Discover thousands of job opportunities and more.");
        description.getStyle().set("color", "#044FA3"); // HEX-Farbcode
        descriptionSection.add(description);

        return descriptionSection;
    }

    private HorizontalLayout createRegistrationSection() {
        HorizontalLayout registrationSection = new HorizontalLayout();

        Button buttonRegStd = getWommBuilder().Button.create("SignUp as Student");
        buttonRegStd.addClickListener( e -> UI.getCurrent().navigate(RegistrierungStudentView.class));
        buttonRegStd.getStyle().set("background-color", "#044FA3"); // HEX-Farbcode
        buttonRegStd.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        registrationSection.add(buttonRegStd);

        Button buttonRegCpny = getWommBuilder().Button.create("SignUp as Company");
        buttonRegCpny.addClickListener( e -> UI.getCurrent().navigate(RegistrierungUnternehmenView.class));
        buttonRegCpny.getStyle().set("background-color", "#044FA3"); // HEX-Farbcode
        buttonRegCpny.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        registrationSection.add(buttonRegCpny);

        Button buttonLog = getWommBuilder().Button.create("LogIn");
        listenEnterForLoginClick(buttonLog);
        buttonLog.addClickListener( e -> UI.getCurrent().navigate(LoginView.class));
        buttonLog.getStyle().set("background-color", "#044FA3"); // HEX-Farbcode
        buttonLog.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        registrationSection.add(buttonLog);

        registrationSection.add(buttonRegStd);
        registrationSection.add(buttonLog);

        return registrationSection;
    }

    private Div createContactInfo() {
        Div contactInfo = new Div();
        contactInfo.add(
                getWommBuilder().Text.create("Want to create company profile? Contact us at kontakt@womm.de.")
        );
        return contactInfo;
    }

    private HorizontalLayout createFooter() {
        HorizontalLayout footer = new HorizontalLayout();
        footer.add(new Anchor("Datenschutzbestimmungen", "# Data protection"));
        footer.add(new Anchor("Nutzungsbedingungen", "# Terms of use"));
        footer.add(new Anchor("Nutzungsbedingungen", "# Imprint"));
        Div createFooter = new Div();
        createFooter.getStyle().set("background", "#044FA3"); // HEX-Farbcode für den Hintergrund
        return footer;
    }
}
