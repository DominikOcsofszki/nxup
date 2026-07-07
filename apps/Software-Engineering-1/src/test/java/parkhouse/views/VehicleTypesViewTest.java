package parkhouse.views;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import parkhouse.Data;
import parkhouse.controller.IParkingController;
import parkhouse.util.Jsonify;

import javax.json.Json;
import javax.json.JsonArray;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VehicleTypesViewTest {

    /*
    Author: jstueh2s
     */

    private final IParkingController controller = Data.controller();

    @Test
    @DisplayName("Test if view generates correct graph")
    public void vehicleTypesViewToStringTest() {
        JsonArray keys = Json.createArrayBuilder()
                .add("QUAD").add("TRIKE")
                .add("SUV").add("PICKUP")
                .add("PKW")
                .build();
        JsonArray values = Json.createArrayBuilder()
                .add(2).add(3)
                .add(2).add(1)
                .add(1)
                .build();
        assertEquals(
                Jsonify.plot(keys, values, "bar", "Types").toString(),
                controller.vehicleTypeView().toString()
        );
    }

}
