package parkhouse.views;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import parkhouse.Data;
import parkhouse.car.ICar;
import parkhouse.controller.IParkingController;
import parkhouse.util.Finder;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DurationViewTest {

    /*
    Author: jstueh2s
     */

    private final IParkingController controller = Data.controller();

    @Test
    @DisplayName("Test if view generates correct graph")
    public void durationViewToStringTest() {
        JsonReader reader = Json.createReader(new StringReader(controller.durationView().toString()));
        JsonObject view = reader.readObject().getJsonArray("data").getJsonObject(0);
        reader.close();
        String[] licenses = new String[]{"SU-J 58","SU-K 19","SU-C 64","SU-G 88","SU-S 83","SU-J 79","SU-S 42","SU-Q 88","SU-B 65"};
        JsonArray x = view.getJsonArray("x");
        JsonArray y = view.getJsonArray("y");
        for (int i = 0; i < x.size(); i++) {
            String l = x.getString(i);
            assertEquals(licenses[i], l);
            assertEquals(
                    Finder.findCar(controller.getAllCars(), ICar::license, l).duration(),
                    Long.parseLong(y.getString(i)), 20000
            );
        }
        assertEquals("bar", view.getString("type"));
        assertEquals("Duration", view.getString("name"));
    }

}
