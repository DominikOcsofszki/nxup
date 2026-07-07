package de.hbrs.se2.womm.views.layouts;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import de.hbrs.se2.womm.config.SecurityService;
import de.hbrs.se2.womm.services.BenachrichtigungService;
import de.hbrs.se2.womm.views.unternehmen.*;

public class UnternehmenLayout extends AbstractLayoutLoggedIn {
    BenachrichtigungService benachrichtigungService;

    protected UnternehmenLayout(SecurityService securityService,
                            BenachrichtigungService benachrichtigungService) {
        super(securityService, benachrichtigungService);
        this.benachrichtigungService = benachrichtigungService;
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        if (securityService.getAuthenticatedUser() == null ||
                (!securityService.isUserUnternehmen() && !securityService.isUserAdmin()))
            UI.getCurrent().navigate(ROUTING.ALL.AccessDeniedView);
    }

    @Override
    void createDrawer() {
        addToDrawer(new VerticalLayout(
                setUpMenuBarWommAndTranslateName("Homepage", UHomepageUnternehmenView.class, VaadinIcon.HOME),
                setUpMenuBarWommAndTranslateName("Profile", UFirmProfileDisplayView.class, VaadinIcon.BUILDING),
                setUpMenuBarWommAndTranslateName("Applications", UApplicationsView.class, VaadinIcon.BRIEFCASE),
                setUpMenuBarWommAndTranslateName("Create advertisement", UStelleAnzeigeErstellenView.class, VaadinIcon.PLUS),
                setUpMenuBarWommAndTranslateName("Edit profile", UEditFirmProfileDisplayView.class, VaadinIcon.EDIT)

        ));
    }
}
