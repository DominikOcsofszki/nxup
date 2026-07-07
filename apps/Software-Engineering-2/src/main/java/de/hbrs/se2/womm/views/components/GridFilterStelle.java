package de.hbrs.se2.womm.views.components;

import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.GridVariant;
import de.hbrs.se2.womm.dtos.StelleDTO;
import de.hbrs.se2.womm.views.extra.VaadinBuilderWomm;
import de.hbrs.se2.womm.views.layouts.ROUTING;

import java.time.format.DateTimeFormatter;

public class GridFilterStelle extends AGridFilter<StelleDTO>{
    public GridFilterStelle() {
        super();
    }

    @Override
    protected void configureGrid() {
        String header1 = VaadinBuilderWomm.translateTextStatic("Advertisement");
        grid.addColumn(StelleDTO::getStelleTitel).setHeader(new Html("<b>" +header1+"</b>"));
        String header2 = VaadinBuilderWomm.translateTextStatic("Firm");
        grid.addColumn(stelleDTO -> stelleDTO.getUnternehmen().getName()).setHeader(new Html("<b>" +header2+"</b>"));
        String header3 = VaadinBuilderWomm.translateTextStatic("Description");
        grid.addColumn(StelleDTO::getStelleBeschreibung).setHeader(new Html("<b>" +header3+"</b>"));
        String header4 = VaadinBuilderWomm.translateTextStatic("Creation date");
        grid.addColumn(stelleDTO -> stelleDTO.getErstellungsdatum().toString().substring(0,16)).setHeader(new Html("<b>" +header4+"</b>"));
//        grid.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT); //TODO Adding to show all Text
//                .setFooter(String.format("%s total Advertisement", 1000));; //TODO any reason for html???/
        //We can also add footer to grid, e.g. with the number of advertisments
        String header5 = VaadinBuilderWomm.translateTextStatic("Type");
        grid.addColumn(StelleDTO::getStelleTyp).setHeader(new Html("<b>" +header5+"</b>"));
        String header6 = VaadinBuilderWomm.translateTextStatic("Location");
        grid.addColumn(StelleDTO::getStelleOrt).setHeader(new Html("<b>" +header6+"</b>"));

    }

    @Override
    protected String[] getFilterByItemsFromDTO() {
        String str1 = VaadinBuilderWomm.translateTextStatic("Advertisement");
        String str2 = VaadinBuilderWomm.translateTextStatic("Firm");
        String str3 = VaadinBuilderWomm.translateTextStatic("Description");
        String str4 = VaadinBuilderWomm.translateTextStatic("Creation date");
        String str5 = VaadinBuilderWomm.translateTextStatic("Type");
        String str6 = VaadinBuilderWomm.translateTextStatic("Location");
        return new String[]{
                str1,
                str2,
                str3,
                str4,
                str5,
                str6
        };
    }

    @Override
    protected String checkItem(StelleDTO dto, String searchBy) {

        return switch (searchBy) {
            case "Stellenanzeige" -> dto.getStelleTitel();
            case "Unternehmen" -> dto.getUnternehmen().toString();
            case "Beschreibung" -> dto.getStelleBeschreibung();
            case "Erstellungsdatum" -> dto.getErstellungsdatum().toString().substring(0,16);
            case "Typ" -> dto.getStelleTyp();
            case "Ort" -> dto.getStelleOrt();
            case "Advertisement" -> dto.getStelleTitel();
            case "Firm" -> dto.getUnternehmen().toString();
            case "Description" -> dto.getStelleBeschreibung();
            case "Creation date" -> dto.getErstellungsdatum().toString().substring(0,16);
            case "Type" -> dto.getStelleTyp();
            case "Location" -> dto.getStelleOrt();
            default -> null;
        };
    }

    public void setColumnClickListener(String location) {
        this.grid.addItemClickListener(item -> UI.getCurrent()
                .navigate(location + "/" + item.getItem().getStelleId()));
    }
}
