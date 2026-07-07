package parkhouse.views;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import parkhouse.Data;
import parkhouse.controller.IParkingController;
import parkhouse.util.Jsonify;

import javax.json.Json;
import javax.json.JsonArray;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClientCategoriesViewTest {

    /*
    Author: jstueh2s
     */

    private final IParkingController controller = Data.controller();

    @Test
    @DisplayName("Test if view generates correct graph")
    public void clientCategoriesViewToStringTest() {
        JsonArray keys = Json.createArrayBuilder()
                .add("Women").add("Family")
                .add("Default").add("Business")
                .build();
        JsonArray values = Json.createArrayBuilder()
                .add(2).add(4)
                .add(1).add(2)
                .build();
        assertEquals(
                Jsonify.plot(keys, values, "bar", "Categories").toString(),
                controller.clientCategoriesView().toString()
        );
    }

}
