package de.hbrs.se2.womm.views.components;

import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import de.hbrs.se2.womm.dtos.StelleDTO;

import java.util.List;

public class Stellenanzeige {
    private final VerticalLayout anzeige;
    private final HorizontalLayout ortLayout;
    private final HorizontalLayout linkLayout;
    private final Div beschreibung;

    public Stellenanzeige() {
        this.ortLayout = new HorizontalLayout();
        this.linkLayout = new HorizontalLayout();
        this.beschreibung = new Div();
        this.anzeige = new VerticalLayout(ortLayout, linkLayout, beschreibung);
    }

    public VerticalLayout fillOutLayout(StelleDTO stelle) {
        createOrtLayout(stelle.getStelleOrt());
        createLinkLayout(stelle.getStelleWebsite());
        createBeschreibung(stelle.getStelleTitel(), stelle.getStelleBeschreibung());
        return this.anzeige;
    }

    private void createOrtLayout(String location) {
        Icon ortsIcon = VaadinIcon.PIN.create();
        Text ort = new Text(location);

        this.ortLayout.add(ortsIcon, ort);
    }

    private void createLinkLayout(String url) {
        Icon linkIcon = VaadinIcon.LINK.create();

        String formattedUrl = formatUrlWithHttpsOrHttpPrefix(url);

        Anchor website = new Anchor(formattedUrl, formattedUrl);
        website.getStyle().setColor("#0000EE");

        this.linkLayout.add(linkIcon, website);
    }

    private void createBeschreibung(String title, String text) {
        this.beschreibung.getStyle().set("margin-top", "20px");

        H3 titel = new H3(title);
        this.beschreibung.add(titel);

        List<String> paragraphs = List.of(text.split("\n\n"));
        paragraphs.forEach(paragraph -> {
            List.of(paragraph.split("\n")).forEach(subParagraph -> {
                Paragraph newParagraph = new Paragraph();
                newParagraph.setWidthFull();
                newParagraph.setText(subParagraph);
                this.beschreibung.add(newParagraph);
            });
            this.beschreibung.add(new Html("<br>"));
        });
    }

    private String formatUrlWithHttpsOrHttpPrefix(String url) {
        String httpsPrefix = "https://";
        String httpPrefix =  "http://";

        return ((url.substring(0,8).equalsIgnoreCase(httpsPrefix) || url.substring(0,7).equalsIgnoreCase(httpPrefix)) ? url : httpsPrefix + url);
    }
}
