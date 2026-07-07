package parkhouse.util;

import parkhouse.car.Car;
import parkhouse.car.ICar;
import parkhouse.config.Config;
import parkhouse.controller.IParkingController;
import parkhouse.security.SanitizedCar;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Saver {

    private static final String PATH = "src/main/resources/";

    private static final Logger LOGGER = Logger.getLogger(Saver.class.getName());

    private final String name;
    private boolean init;
    private boolean initConfig;

    /*
    Author: jstueh2s & docsof2s
     */

    public Saver(String name) {
        init = true;
        initConfig = true;
        this.name = name;
    }

    public boolean init() {
        if (init) {
            init = false;
            return true;
        }
        return false;
    }

    public boolean initConfig() {
        if (initConfig) {
            initConfig = false;
            return true;
        }
        return false;
    }

    public void saveCars(IParkingController controller) {
        Path path = Paths.get(PATH + name + ".cars");
        try (BufferedWriter bw = Files.newBufferedWriter(path, StandardCharsets.US_ASCII)) {
            for (ICar c : controller.getAllCars()) {
                bw.write(c.toString() + "\n");
            }
        } catch (IOException e) {
            LOGGER.warning("Save Cars Failed: " + e.getMessage());
        }
    }

    public void loadCars(IParkingController controller) {
        Path path = Paths.get(PATH + name + ".cars");
        try (BufferedReader br = Files.newBufferedReader(path, StandardCharsets.US_ASCII)) {
            List<String> lines = new ArrayList<>();
            br.lines().collect(Collectors.toCollection(() -> lines));
            for (String l : lines) {
                ICar car = new SanitizedCar(new Car(l.split("/")));
                if (!car.gone()) {
                    controller.addCar(car);
                } else {
                    controller.addRemovedCar(car);
                }
            }
        } catch (IOException e) {
            LOGGER.warning("Load Cars Failed: " + e.getMessage());
        }
    }

    public void saveConfig(int[] config) {
        Path path = Paths.get(PATH + name + ".conf");
        try (BufferedWriter bw = Files.newBufferedWriter(path, StandardCharsets.US_ASCII)) {
            bw.write(String.format("%d,%d,%d", config[0], config[1], config[2]));
        } catch (IOException e) {
            LOGGER.warning("Save Config Failed: " + e.getMessage());
        }
    }

    public int[] loadConfig() {
        Path path = Paths.get(PATH + name + ".conf");
        try (BufferedReader br = Files.newBufferedReader(path, StandardCharsets.US_ASCII)) {
            return Arrays.stream(br.lines().findFirst().orElseThrow(IOException::new).split(","))
                    .mapToInt(Integer::parseInt).toArray();
        } catch (IOException e) {
            LOGGER.warning("Load Config Failed: " + e.getMessage());
        }
        return new int[]{
                Config.DEFAULT_MAX_CARS,
                Config.DEFAULT_OPEN_FROM,
                Config.DEFAULT_OPEN_TO
        };
    }

}
