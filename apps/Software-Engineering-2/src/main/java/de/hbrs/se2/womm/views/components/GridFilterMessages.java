package de.hbrs.se2.womm.views.components;

import de.hbrs.se2.womm.dtos.BenachrichtigungDTO;
import de.hbrs.se2.womm.views.extra.VaadinBuilderWomm;

public class GridFilterMessages extends AGridFilter<BenachrichtigungDTO> {
    @Override
    protected void configureGrid() {
        String nachricht = VaadinBuilderWomm.translateTextStatic("Message");
        grid.addColumn(BenachrichtigungDTO::getNachricht).setHeader(nachricht).setAutoWidth(true);
        String order = VaadinBuilderWomm.translateTextStatic("Order");
        grid.addColumn(BenachrichtigungDTO::getId).setHeader(order).setSortable(true);
//        String gelesen = VaadinBuilderWomm.translateTextStatic("Read");
//        grid.addColumn(BenachrichtigungDTO::isGelesen).setHeader(gelesen);
//        grid.setWidthFull();
//        grid.setHeightFull();
//        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }

    @Override
    protected String[] getFilterByItemsFromDTO() {
        String nachricht = VaadinBuilderWomm.translateTextStatic("Message");
//        String gelesen = VaadinBuilderWomm.translateTextStatic("Read");
//        return new String[]{nachricht, gelesen};
        return new String[]{nachricht};
    }

    @Override
    protected String checkItem(BenachrichtigungDTO dto, String searchBy) {
        return switch (searchBy) {
//            case "benachrichtigungId" -> String.valueOf(dto.());
            case "Message" -> dto.getNachricht();
//            case "Read" -> String.valueOf(dto.isGelesen());
            case "Nachricht" -> dto.getNachricht();
//            case "Gelesen" -> String.valueOf(dto.isGelesen());
            default -> "";
        };
    }
}
