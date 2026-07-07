package de.hbrs.se2.womm.views.utils;

import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.util.List;

public class TextToParagraphFormatter {
    public static VerticalLayout formatTextToParagraph(String text) {
        VerticalLayout layout = new VerticalLayout();
        List.of(text.split("\n\n")).forEach(paragraph -> List.of(paragraph.split("\n")).forEach(subParagraph -> {
            Paragraph newParagraph = new Paragraph(subParagraph);
            layout.add(newParagraph);
        }));
        return layout;
    }
}
