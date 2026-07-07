package parkhouse.views;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import parkhouse.Data;
import parkhouse.calculations.Price;
import parkhouse.controller.IParkingController;
import parkhouse.util.Tableize;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EarningsByCategoriesViewTest {

    /*
    Author: jstueh2s
     */

    private final IParkingController controller = Data.controller();

    @Test
    @DisplayName("Test if view generates correct table")
    public void earningsByCategoriesViewToStringTest() {
        String[] headers = new String[] {"Category", "Sum", "Average", "Minimum", "Maximum"};
        String[][] rows = new String[][] {
                {"Default", Price.format(5819), Price.format(2909), Price.format(601), Price.format(5218)},
                {"Women", Price.format(801), Price.format(801), Price.format(801), Price.format(801)},
                {"Business", Price.format(401), Price.format(401), Price.format(401), Price.format(401)},
                {"Family", Price.format(0), Price.format(0), Price.format(0), Price.format(0)},
                {"Disability", Price.format(3206), Price.format(1603), Price.format(400), Price.format(2806)}
        };
        assertEquals(Tableize.table(headers, rows), controller.earningsByCategoriesView().toString());
    }

}
