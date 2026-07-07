package parkhouse.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import parkhouse.Data;
import parkhouse.car.ICar;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JsonifyTest {

    /*
    Author: jstueh2s
     */

    private final List<ICar> cars = Data.cars();

    @Test
    @DisplayName("Test if json array contains the correct values")
    public void jsonifyCarsAsJsonArrayTest() {
        List<Function<ICar,Object>> func = Arrays.asList(ICar::ticket, ICar::license, ICar::begin);
        for (Function<ICar,Object> f : func) {
            JsonArray arr = Jsonify.carsAsJsonArray(cars, f);
            for (int i = 0; i < cars.size(); i++) {
                assertEquals(f.apply(cars.get(i)).toString(), arr.getString(i));
            }
        }
    }

    @Test
    @DisplayName("Test if car properties are counted correctly")
    public void jsonifyCarsCountTest() {
        List<String> types = List.of("\"QUAD\"", "\"TRIKE\"", "\"SUV\"", "\"PICKUP\"", "\"PKW\"");
        List<String> counts = List.of("4", "5", "2", "3", "1");
        JsonObject count = Jsonify.carsCount(cars, ICar::type);
        List<String> keys = Jsonify.getKeys(count).stream().map(JsonValue::toString).collect(Collectors.toList());
        List<String> values = Jsonify.getValues(count).stream().map(JsonValue::toString).collect(Collectors.toList());
        for (String t : types) {
            assertTrue(keys.contains(t));
        }
        for (String c : counts) {
            assertTrue(values.contains(c));
        }
    }

    @ParameterizedTest
    @DisplayName("Test if correct plot object is build")
    @CsvSource({"bar,BarPlot","line,LinePlot","pie,PiePlot"})
    public void jsonifyPlotTest(String type, String name) {
        JsonArray duration = Jsonify.carsAsJsonArray(cars, ICar::duration);
        JsonObject plot = Jsonify.plot(Jsonify.carsAsJsonArray(cars, ICar::nr), duration, type, name);
        JsonObject data = (JsonObject) plot.getJsonArray("data").get(0);
        assertEquals(Jsonify.carsAsJsonArray(cars, ICar::nr), data.getJsonArray("x"));
        assertEquals(duration, data.getJsonArray("y"));
        assertEquals(type, data.getString("type"));
        assertEquals(name, data.getString("name"));
    }

}
