package parkhouse.calculations;

import parkhouse.car.ICar;
import parkhouse.config.Config;

import java.util.*;

public class Price {

    /*
    Author: jstueh2s
     */

    private Price() {}

    public static String format(long price) {
        return String.format(Locale.US, "%.2f€", price / 100d);
    }

    public static double priceFactor(ICar car) {
        List<String> priceFactors = new ArrayList<>(Arrays.asList(Config.priceFactor()));

        OptionalDouble categoryType = priceFactors.stream()
                .filter(p -> p.matches("^[A-Za-z]+\\.[A-Za-z]+:[0-9.]+"))
                .map(p -> p.split(":"))
                .filter(p -> p[0].split("\\.")[0].equals(car.category()))
                .filter(p -> p[0].split("\\.")[1].equals(car.type()))
                .mapToDouble(p -> Double.parseDouble(p[1])).findFirst();
        if (categoryType.isPresent()) return categoryType.getAsDouble();

        OptionalDouble category = priceFactors.stream()
                .filter(p -> p.matches("^[A-Za-z]+:[0-9.]+"))
                .map(p -> p.split(":")).filter(p -> p[0].equals(car.category()))
                .mapToDouble(p -> Double.parseDouble(p[1])).findFirst();
        if (category.isPresent()) return category.getAsDouble();

        OptionalDouble type = priceFactors.stream()
                .filter(p -> p.matches("^[A-Za-z]+:[0-9.]+"))
                .map(p -> p.split(":")).filter(p -> p[0].equals(car.type()))
                .mapToDouble(p -> Double.parseDouble(p[1])).findFirst();
        if (type.isPresent()) return type.getAsDouble();

        return 1d;
    }

}
