package de.hbrs.se2.womm.views.components;

import com.vaadin.flow.component.UI;
import de.hbrs.se2.womm.dtos.BewerbungDTO;
import de.hbrs.se2.womm.views.extra.VaadinBuilderWomm;
import de.hbrs.se2.womm.views.layouts.ROUTING;

public class GridFilterBewerbung extends AGridFilter<BewerbungDTO> {

    @Override
    protected void configureGrid() {
        String nachname = VaadinBuilderWomm.translateTextStatic("Last name");
        grid.addColumn(bewerbungDTO -> bewerbungDTO.getBewerbungStudent().getStudentName()).setHeader(nachname);
        String vorname = VaadinBuilderWomm.translateTextStatic("First name");
        grid.addColumn(bewerbungDTO -> bewerbungDTO.getBewerbungStudent().getStudentVorname()).setHeader(vorname);
        String bewerbung = VaadinBuilderWomm.translateTextStatic("Application");
        grid.addColumn(BewerbungDTO::getBewerbungText).setHeader(bewerbung);
        String status = VaadinBuilderWomm.translateTextStatic("State");
        grid.addColumn(BewerbungDTO::getBewerbungStatus).setHeader(status);
    }

    @Override
    protected String[] getFilterByItemsFromDTO() {
        String nachname = VaadinBuilderWomm.translateTextStatic("Last name");
        String vorname = VaadinBuilderWomm.translateTextStatic("First name");
        String bewerbung = VaadinBuilderWomm.translateTextStatic("Application");
        String status = VaadinBuilderWomm.translateTextStatic("State");
        return new String[]{
                nachname,
                vorname,
                bewerbung,
                status
        };
    }

    @Override
    protected String checkItem(BewerbungDTO dto, String searchBy) {
        return switch (searchBy) {
            case "Last name" -> dto.getBewerbungStudent().getStudentName();
            case "First name" -> dto.getBewerbungStudent().getStudentVorname();
            case "Nachname" -> dto.getBewerbungStudent().getStudentName();
            case "Vorname" -> dto.getBewerbungStudent().getStudentVorname();
            default -> null;
        };
    }

    public void setColumnClickListener(String location) {
        this.grid.addItemClickListener(item -> UI.getCurrent()
                .navigate(location + "/" + (item.getItem()).getBewerbungId()));
    }
}
