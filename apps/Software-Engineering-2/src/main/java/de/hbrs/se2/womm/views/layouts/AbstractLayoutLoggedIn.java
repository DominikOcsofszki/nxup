package de.hbrs.se2.womm.views.layouts;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import de.hbrs.se2.womm.config.SecurityService;
import de.hbrs.se2.womm.services.BenachrichtigungService;
import de.hbrs.se2.womm.views.extra.VaadinBuilderWomm;
import de.hbrs.se2.womm.views.student.SChatView;
import de.hbrs.se2.womm.views.unternehmen.UNotificationView;

abstract class AbstractLayoutLoggedIn extends AbstractLayout {
    protected final SecurityService securityService;
    BenachrichtigungService benachrichtigungService;

    AbstractLayoutLoggedIn(SecurityService securityService,
                           BenachrichtigungService benachrichtigungService) {
        this.benachrichtigungService = benachrichtigungService;
        this.securityService = securityService;
        String username = securityService.getAuthenticatedUser() == null ? null :
                securityService.getAuthenticatedUser().getUsername();
        long nutzerId = securityService.getLoggedInNutzerID();

        String text = VaadinBuilderWomm.translateTextStatic("Log out"); //TODO better change to this?
        Button logoutButton = new Button(text, e -> securityService.logout());
//        super.createHeaderWithLogoutButton(logoutButton, true, addBenachrichtigungen());
        super.createHeaderWithLogoutButton(logoutButton, true,
                addBenachrichtigungen(), securityService.isUserStudent());
    }

    int getMessagesCount() {
        long nutzerId = securityService.getLoggedInNutzerID();
        int nrOfMessages = benachrichtigungService.getAnzahlUngelesenByNutzerID(nutzerId);
        return nrOfMessages;
    }

    MenuBar addBenachrichtigungen() {
        boolean isStudent = securityService.isUserStudent();
        long messages = getMessagesCount();
        Span inboxCounter = new Span(VaadinIcon.COMMENT.create(), new Span(" " + messages));
        MenuBar menu = new MenuBar();
        menu.setOpenOnHover(true);
        if (isStudent) {
            menu.addItem(inboxCounter, e -> UI.getCurrent().navigate(SChatView.class));
        } else {
            menu.addItem(inboxCounter, e -> UI.getCurrent().navigate(UNotificationView.class));
        }
        return menu;
    }

    MenuBar setUpMenuBarWommAndTranslateName(String name, Class<? extends Component> view) {
        MenuBar menuBar = new MenuBar();
        menuBar.addThemeVariants(MenuBarVariant.LUMO_ICON);
        menuBar.setOpenOnHover(true);
        menuBar.isOpenOnHover();
        menuBar.setThemeName("blue");
        menuBar.addItem(VaadinBuilderWomm.translateTextStatic(name),
                e -> UI.getCurrent().navigate(view));
        menuBar.setWidthFull();
        return menuBar;
    }

    MenuBar setUpMenuBarWommAndTranslateName(String name, Class<? extends Component> view, VaadinIcon iconName) {
        Icon icon = new Icon(iconName);
        MenuBar menuBar = setUpMenuBarWommAndTranslateName(name, view);
        menuBar.getChildren().findFirst().get().getElement().insertChild(0, icon.getElement());       //left side
        return menuBar;
    }
}
