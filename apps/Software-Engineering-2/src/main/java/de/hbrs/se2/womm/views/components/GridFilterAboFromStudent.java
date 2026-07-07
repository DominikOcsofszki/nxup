package de.hbrs.se2.womm.views.components;

import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.UI;
import de.hbrs.se2.womm.dtos.AboDTO;
import de.hbrs.se2.womm.dtos.StelleDTO;
import de.hbrs.se2.womm.views.extra.VaadinBuilderWomm;
import de.hbrs.se2.womm.views.layouts.ROUTING;

public class GridFilterAboFromStudent extends AGridFilter<AboDTO>{

    @Override
    protected void configureGrid() {
        String unternehmen = VaadinBuilderWomm.translateTextStatic("Firm");
        grid.addColumn(AboDTO -> AboDTO.getUnternehmen().getName()).setHeader(new Html("<b>" +unternehmen+"</b>"));
        String beschreibung = VaadinBuilderWomm.translateTextStatic("Description");
        grid.addColumn(AboDTO -> AboDTO.getUnternehmen().getBeschreibung()).setHeader(new Html("<b>" +beschreibung+"</b>"));
        setColumnClickListener();
    }

    @Override
    protected String[] getFilterByItemsFromDTO() {
        String unternehmen = VaadinBuilderWomm.translateTextStatic("Firm");
        String beschreibung = VaadinBuilderWomm.translateTextStatic("Description");
        return new String[]{
            unternehmen,
            beschreibung
        };
    }

    @Override
    protected String checkItem(AboDTO dto, String searchBy) {
        return switch (searchBy) {
            case "Firm" -> dto.getUnternehmen().getName();
            case "Description" -> dto.getUnternehmen().getBeschreibung();
            case "Unternehmen" -> dto.getUnternehmen().getName();
            case "Beschreibung" -> dto.getUnternehmen().getBeschreibung();
            default -> null;
        };
    }
    public void setColumnClickListener() {
        this.grid.addItemClickListener(item -> UI.getCurrent()
                .navigate(ROUTING.STUDENT.SFirmProfileDisplayView + "/" + item.getItem().getUnternehmen().getUnternehmenId()));
    }
}
