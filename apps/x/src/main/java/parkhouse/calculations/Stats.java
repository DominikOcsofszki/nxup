package parkhouse.calculations;

import parkhouse.car.ICar;

import java.util.List;

public class Stats {

     /*
    Author: tpapen2s & docsof2s
     */

    private Stats() {}

    public static long minCars(List<ICar> cars) {
        return cars.stream()
                .mapToLong(ICar::price)
                .min()
                .orElse(0L);
    }

    public static long maxCars(List<ICar> cars) {
        return cars.stream()
                .mapToLong(ICar::price)
                .max()
                .orElse(0L);
    }

    public static long sumCars(List<ICar> cars) {
        return cars.stream()
                .mapToLong(ICar::price)
                .sum();
    }

    public static long avgCars(List<ICar> cars) {
        return sumCars(cars) / Math.max(cars.size(), 1);
    }

}
