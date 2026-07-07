package de.hbrs.se2.womm.views.components;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;
import de.hbrs.se2.womm.dtos.AbstractDTO;
import de.hbrs.se2.womm.views.extra.VaadinBuilderWomm;

import java.util.List;

public abstract class AGridFilter<ExtendAbstractDTO extends AbstractDTO>
        extends VerticalLayout {

    TextField filterText = new TextField();
    Select<String> select = new Select<>();
    protected Grid<ExtendAbstractDTO> grid = new Grid<>();
    public static String[] filterByItemsFromDTO;

    public AGridFilter() {
        this.filterByItemsFromDTO = getFilterByItemsFromDTO();
        setUpFilter();
    }
    public void setUpFromOutside(List<ExtendAbstractDTO> list) {
        setUpGrid(list);
        setUpToolbarAndAddGrid();
    }


    private void setUpFilter() {
        setFilterBy(filterByItemsFromDTO[0]);
        select.addValueChangeListener(event -> setFilterBy(event.getValue()));
    }

    private void setUpGrid(List<ExtendAbstractDTO> itemsForGrid) {
        addClassName("list-view");
        setSizeFull();
        configureGrid();
        List<ExtendAbstractDTO> list = itemsForGrid;
        if (list!= null) grid.setItems(list);
    }

    protected abstract void configureGrid();

    private void setUpToolbarAndAddGrid() {
        filterText.setPlaceholder(VaadinBuilderWomm.translateTextStatic("Filter by..."));
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.setValueChangeTimeout(300);
        select.setPlaceholder("Filter");
        select.setItems(filterByItemsFromDTO);
        HorizontalLayout toolbar = new HorizontalLayout(filterText, select);
        toolbar.addClassName("toolbar");
//        grid.setAllRowsVisible(true); //TODO do wen want that?
//        grid.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS); //More options
//        grid.addThemeVariants(GridVariant.LUMO_WRAP_CELL_CONTENT); //TODO Adding to show all Text
        grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        add(toolbar, grid);
    }

    private void setFilterBy(String searchBy) {
        filterText.addValueChangeListener(
                (AbstractField.ComponentValueChangeEvent<TextField, String> event)
                        -> {
                    String inputSearchNameFilter = event.getValue();
                    ((ListDataProvider<ExtendAbstractDTO>) grid.getDataProvider()).
                            setFilter((ExtendAbstractDTO dto)
                                    -> filterFunction(dto, inputSearchNameFilter, searchBy));
                }
        );
    }

    boolean compareFilteringToLowerCase(String checkItem, String inputSearchNameFilter) {
        inputSearchNameFilter = inputSearchNameFilter.toLowerCase();
        String checkItemLowerCase = checkItem.toLowerCase();
        return checkItemLowerCase.contains(inputSearchNameFilter);
    }

    boolean filterFunction(ExtendAbstractDTO dto, String inputSearchNameFilter, String searchBy) {
        String checkItem = checkItem(dto, searchBy);
        return compareFilteringToLowerCase(checkItem, inputSearchNameFilter);
    }

    protected abstract String[] getFilterByItemsFromDTO();

    protected abstract String checkItem(ExtendAbstractDTO dto, String searchBy);
}
