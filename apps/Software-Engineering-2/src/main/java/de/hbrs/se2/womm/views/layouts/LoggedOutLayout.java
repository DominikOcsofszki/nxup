package de.hbrs.se2.womm.views.layouts;

public class LoggedOutLayout extends AbstractLayout {

    protected LoggedOutLayout() {
        super.createHeaderWithLogoutButton(null, false, null);
    }
}
