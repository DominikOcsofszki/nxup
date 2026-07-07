package parkhouse.car;

import java.util.HashMap;
import java.util.Map;

public class CarTypes {

    private static final Map<String, CarTypes> types = new HashMap<>();
    private double factor;

    /*
    Author: staher2s
     */

    private CarTypes() {}

    public static CarTypes getInstance(String key) {
        return types.computeIfAbsent(key, k -> new CarTypes());
    }

    public double getFactor() {
        return factor;
    }

    public void setFactor(double factor) {
        if (factor <= 0) {
            throw new IllegalArgumentException();
        }
        this.factor = factor;
    }

}