package parkhouse.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TableizeTest {

    /*
    Author: jstueh2s
     */

    private final String[] headers = new String[] {"Header 1", "Header 2", "Header 3"};
    private final String[][] rows = new String[][] {
            {"Data 1.1", "Data 1.2", "Data 1.3"},
            {"Data 2.1", "Data 2.2", "Data 2.3"},
            {"Data 3.1", "Data 3.2", "Data 3.3"}};

    @Test
    @DisplayName("Test if html table is build correctly")
    public void tableizeTableTest() {
        String table = "<table>" +
                "<tr><th>Header 1</th><th>Header 2</th><th>Header 3</th></tr>" +
                "<tr><td>Data 1.1</td><td>Data 1.2</td><td>Data 1.3</td></tr>" +
                "<tr><td>Data 2.1</td><td>Data 2.2</td><td>Data 2.3</td></tr>" +
                "<tr><td>Data 3.1</td><td>Data 3.2</td><td>Data 3.3</td></tr>" +
                "</table>";
        assertEquals(table, Tableize.table(headers, rows));
    }

}
