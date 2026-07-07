package parkhouse.controller;

import org.junit.jupiter.api.*;
import parkhouse.Data;
import parkhouse.car.Car;
import parkhouse.car.ICar;
import parkhouse.commands.CarEnterCommand;
import parkhouse.util.Time;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class ParkingControllerTest {

    /*
    Author: docsof2s
     */

    private IParkingController parkingController;
    private final List<String[]> param = Data.paramsDuration();
    private List<ICar> carList = new ArrayList<>();

    private final ICar carNow = new Car(new String[]{Data.NR, Time.now() - 10000+"",
            Data.DURATION,Data.PRICE,Data.TICKET, Data.COLOR,Data.SPACE,Data.CATEGORY,Data.TYPE,
            Data.LICENSE,Time.now() - 10000+""});

    private final ICar carYesterday = new Car(new String[]{Data.NR, Time.now() - Time.MILLISECONDS_PER_DAY - 10000+"",
            Data.DURATION,Data.PRICE,Data.TICKET,Data.COLOR,Data.SPACE,Data.CATEGORY,Data.TYPE,
            Data.LICENSE,Time.now() -  Time.MILLISECONDS_PER_DAY - 10000+""});

    private final ICar carLastWeek = new Car(new String[]{Data.NR, Time.now() - Time.MILLISECONDS_PER_WEEK - 10000+"",
            Data.DURATION,Data.PRICE,Data.TICKET, Data.COLOR,Data.SPACE,Data.CATEGORY,Data.TYPE,
            Data.LICENSE,Time.now() - Time.MILLISECONDS_PER_WEEK - 10000+""});


    @BeforeEach
    void setup() {
        parkingController = new ParkingController();
        for (String[] s : param) {
            carList.add(new Car(s));
        }
    }

    @AfterEach
    void teardown() {
        parkingController = null;
        carList = null;
    }


    @Test
    @DisplayName("Test if the added Car is in the getCars list")
    void parkingControllerAddCarTest() {
        for (int i = 0; i < carList.size(); i++) {
            parkingController.addCar(carList.get(i));
            assertEquals(i + 1, parkingController.getCars().size());
        }
    }

    @Test
    @DisplayName("Test if the removed car is in the getRemovedCars list")
    void parkingControllerRemoveCarTest() {
        for (ICar iCar : carList) {
            parkingController.addCar(iCar);
        }
        assertEquals(param.size(), parkingController.getCars().size());
        for (int i = 0; i < carList.size(); i++) {
            parkingController.removeCar(carList.get(i));
            assertEquals(i + 1, parkingController.getRemovedCars().size());
            assertEquals(carList.size() - 1 - i, parkingController.getCars().size());
        }
    }

    @Test
    @DisplayName("Tests if the deleted car is in now list")
    void parkingControllerDeleteCarTest() {
        for (int i = 0; i < carList.size(); i++) {
            parkingController.addCar(carList.get(i));
            if (i % 2 == 0) parkingController.removeCar(carList.get(i));
            assertEquals(i + 1, parkingController.getAllCars().size());
        }
        for (int i = 0; i < carList.size(); i++) {
            parkingController.deleteCar(carList.get(i));
            assertEquals(carList.size() - i - 1, parkingController.getAllCars().size());
        }
    }

    @Test
    @DisplayName("Tests if the add car is in the list")
    void parkingCOntrollerAddCarRestartServerTest() {
        for (int i = 0; i < carList.size(); i++) {
            parkingController.addCar(carList.get(i));
            assertEquals(i + 1, parkingController.getCars().size());
        }
    }

    @Test
    @DisplayName("Tests if the removed car is in the list")
    void parkingControllerRemoveCarRestartServerTest() {
        for (int i = 0; i < carList.size(); i++) {
            parkingController.addRemovedCar(carList.get(i));
            assertEquals(i + 1, parkingController.getRemovedCars().size());
        }
    }

    @Test
    @DisplayName("Tests if the daily earnings view is returned")
    void parkingControllerDailyEarningsViewTest() {
        parkingController.addCar(carNow);
        parkingController.removeCar(carNow);
        assertEquals(69, parkingController.dailyEarningsView().getDailyEarnings());
        parkingController.addCar(carYesterday);
        parkingController.removeCar(carYesterday);
        assertEquals(69, parkingController.dailyEarningsView().getDailyEarnings());
    }

    @Test
    @DisplayName("Tests if the weekly earnings view is returned")
    void parkingControllerWeeklyEarningsViewTest() {
        parkingController.addCar(carNow);
        parkingController.removeCar(carNow);
        assertEquals(69, parkingController.weeklyEarningsView().getWeeklyEarnings());
        parkingController.addCar(carLastWeek);
        parkingController.removeCar(carLastWeek);
        assertEquals(69, parkingController.weeklyEarningsView().getWeeklyEarnings());
    }

    @Test
    @DisplayName("Tests if the current cost view is returned")
    void parkingControllerCurrentCostViewTest() {
        for (ICar car : carList) {
            parkingController.addCar(car);
            assertEquals(car.price(), parkingController.currentCostView().getCurrentCosts().get(car.license()));
        }
    }

    @Test
    @DisplayName("Tests if the commander is returned")
    void parkingControllerCommanderTest() {
        for (ICar car : carList) {
            parkingController.commander().queue(new CarEnterCommand(car, parkingController));
            parkingController.commander().activate();
        }
        assertEquals(carList.size(), parkingController.getCars().size());
    }
}
