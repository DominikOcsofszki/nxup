package parkhouse;

import parkhouse.car.Car;
import parkhouse.car.ICar;
import parkhouse.controller.IParkingController;
import parkhouse.controller.ParkingController;


import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Data {

    public static final String NR = "25";
    public static final String DURATION = "6010";
    public static final String PRICE = "69";
    public static final String TICKET = "a7aa53882766f4bf361ca339fb843fa9";
    public static final String COLOR = "#42671f";
    public static final String SPACE = "2";
    public static final String CATEGORY = "Women";
    public static final String TYPE = "SUV";
    public static final String LICENSE = "SU-K 41";

    private static final Logger LOGGER = Logger.getLogger(Data.class.getName());

    /*
    Author: jstueh2s
     */

    private Data() {}

    public static List<String[]> params() {
        return loadParams("src/test/resources/cars.csv");
    }

    public static List<String[]> paramsDuration() {
        return loadParams("src/test/resources/cars_with_duration.csv");
    }

    private static List<String[]> loadParams(String p) {
        Path path = Paths.get(p);
        List<String[]> params = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(path, StandardCharsets.US_ASCII)) {
            List<String> lines = new ArrayList<>();
            br.lines().collect(Collectors.toCollection(() -> lines));
            for (String l : lines) {
                params.add(l.split(","));
            }
        } catch(IOException e) {
            LOGGER.warning("Load Data Failed: " + e.getMessage());
        }
        return params;
    }

    public static List<ICar> cars() {
        List<String[]> params = params();
        List<ICar> cars = new ArrayList<>();
        for (String[] p : params) {
            cars.add(new Car(p));
        }
        return cars;
    }

    public static IParkingController controller() {
        List<ICar> cars = cars();
        IParkingController controller = new ParkingController();
        for (ICar c : cars) {
            if (c.gone()) {
                controller.addRemovedCar(c);
            } else {
                controller.addCar(c);
            }
        }
        return controller;
    }

}
