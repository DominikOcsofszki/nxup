package parkhouse.calculations;

import org.junit.jupiter.api.*;
import parkhouse.Data;
import parkhouse.car.Car;
import parkhouse.car.ICar;
import parkhouse.controller.IParkingController;
import parkhouse.controller.ParkingController;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StatsTest {

    /*
    Author: tpapen2s
     */

    private final List<String[]> params = Data.paramsDuration();
    private List<ICar> carList = new ArrayList<>();
    private IParkingController parkingController;
    private static final int TEST_CAR_SUM = 10601;

    @BeforeEach
    void setup() {
        parkingController = new ParkingController();
        for(String[] s : params) {
            carList.add(new Car(s));
        }
    }

    @AfterEach
    void teardown() {
        parkingController = null;
        carList = null;
    }

    @Test
    @DisplayName("tests if the min price is right in a given list")
    void statsMinCarsTest() {
        for(ICar car : carList) {
            parkingController.addCar(car);
            parkingController.removeCar(car);
        }
        assertEquals(201, Stats.minCars(carList));
    }

    @Test
    @DisplayName("tests if the max price is right in a given list")
    void statsMaxCarsTest() {
        for(ICar car : carList) {
            parkingController.addCar(car);
            parkingController.removeCar(car);
        }
        assertEquals(4000, Stats.maxCars(carList));
    }

    @Test
    @DisplayName("tests if sumCars has the right sum")
    void statsSumCarsTest() {
        for(ICar car : carList) {
            parkingController.addCar(car);
            parkingController.removeCar(car);
        }
        assertEquals(TEST_CAR_SUM, Stats.sumCars(carList));
    }

    @Test
    @DisplayName("tests if avgCars has the right avg")
    void statsAvgCarsTest() {
        for(ICar car : carList) {
            parkingController.addCar(car);
            parkingController.removeCar(car);
        }
        assertEquals(TEST_CAR_SUM / params.size(), Stats.avgCars(carList));
    }

    @Test
    @DisplayName("tests avgCars with no cars to prevent division by 0")
    void statsAvgCarsNoCarsTest() {
        List<ICar> tmpList = new ArrayList<>();
        assertEquals(0, Stats.avgCars(tmpList));
    }


}
