package parkhouse.util;

import parkhouse.car.ICar;

import javax.json.*;
import java.util.*;
import java.util.function.Function;

public class Jsonify {

    /*
    Author: jstueh2s
     */

    private Jsonify() {}

    public static JsonArray carsAsJsonArray(List<ICar> cars, Function<ICar,Object> func) {
        JsonArrayBuilder arr = Json.createArrayBuilder();
        for (ICar c : cars) {
            arr.add(Json.createValue(func.apply(c).toString()));
        }
        return arr.build();
    }

    public static JsonObject carsCount(List<ICar> cars, Function<ICar,Object> func) {
        JsonObjectBuilder obj = Json.createObjectBuilder();
        HashMap<String,Integer> count = new HashMap<>();
        for (ICar c : cars) {
            String key = func.apply(c).toString();
            if (!count.containsKey(key)) {
                count.put(key, 1);
            } else {
                count.put(key, count.get(key) + 1);
            }
        }
        for (Map.Entry<String,Integer> e : count.entrySet()) {
            obj.add(e.getKey(), e.getValue());
        }
        return obj.build();
    }

    public static JsonObject plot(JsonArray x, JsonArray y, String type, String name) {
        return Json.createObjectBuilder()
                .add("data", Json.createArrayBuilder()
                        .add(Json.createObjectBuilder()
                                .add("x", x)
                                .add("y", y)
                                .add("type", type)
                                .add("name", name)
                        )).build();
    }

    public static JsonArray getKeys(JsonObject obj) {
        JsonArrayBuilder arr = Json.createArrayBuilder();
        for (String k : obj.keySet()) {
            arr.add(k);
        }
        return arr.build();
    }

    public static JsonArray getValues(JsonObject obj) {
        JsonArrayBuilder arr = Json.createArrayBuilder();
        for (Map.Entry<String,JsonValue> v : obj.entrySet()) {
            arr.add(v.getValue());
        }
        return arr.build();
    }

}
