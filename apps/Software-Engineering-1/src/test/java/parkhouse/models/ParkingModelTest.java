package parkhouse.models;

import org.junit.jupiter.api.*;
import parkhouse.Data;
import parkhouse.car.Car;
import parkhouse.car.ICar;
import parkhouse.util.Time;
import parkhouse.views.DailyEarningsView;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ParkingModelTest {

    /*
    Author: tpapen2s
     */

    private ParkingModel parkingModel;
    private final List<String[]> params = Data.paramsDuration();
    private final List<String[]> params2 = Data.params();
    private final List<ICar> carsList = new ArrayList<>();
    private final List<ICar> carsNoDurationList = new ArrayList<>();

    @BeforeEach
    void setup() {
        parkingModel = new ParkingModel();
        for(String[] s : params) {
            carsList.add(new Car(s));
        }
        for(String[] s : params2) {
            carsNoDurationList.add(new Car(s));
        }
    }

    @AfterEach
    void teardown() {
        parkingModel = null;
    }

    @Test
    @DisplayName("test register and remove")
    void parkingModelObserverTest() {
        DailyEarningsView dailyEarningsView = new DailyEarningsView(parkingModel);
        parkingModel.removeObserver(dailyEarningsView);
        parkingModel.registerObserver(dailyEarningsView);
        ICar x = new Car(new String[]{Data.NR, Time.now() - 10000+"",Data.DURATION,Data.PRICE,Data.TICKET,
                Data.COLOR,Data.SPACE,Data.CATEGORY,Data.TYPE,Data.LICENSE,Time.now() - 10000+""});
        parkingModel.addCar(x);
        parkingModel.removeCar(x);
        assertEquals(69, dailyEarningsView.getDailyEarnings());
        parkingModel.removeObserver(dailyEarningsView);
        ICar y = new Car(new String[]{Data.NR, Time.now() - 10000+"",Data.DURATION,Data.PRICE,Data.TICKET,
                Data.COLOR,Data.SPACE,Data.CATEGORY,Data.TYPE,Data.LICENSE,Time.now() - 10000+""});
        parkingModel.addCar(y);
        parkingModel.removeCar(y);
        assertEquals(69, dailyEarningsView.getDailyEarnings());
    }

    @Test
    @DisplayName("test if the addCar method adds the right cars to the list")
    void parkingModelAddCarTest() {
        for(String[] s : params) {
            parkingModel.addCar(new Car(s));
        }
        assertEquals(params.size(), parkingModel.getCarList().size());
        for(int i = 0; i < carsList.size(); i++) {
            assertEquals(carsList.get(i).license(), parkingModel.getCarList().get(i).license());
        }
    }

    @Test
    @DisplayName("test if the removeCar method removes cars from the cars list")
    void parkingModelRemoveCarTest() {
        for(String[] s : params) {
            ICar c = new Car(s);
            parkingModel.addCar(c);
            parkingModel.removeCar(c);
            assertFalse(parkingModel.getCarList().contains(c));
        }
        assertEquals(0, parkingModel.getCarList().size());
    }

    @Test
    @DisplayName("tests if the removedCar list has every car that has been removed")
    void parkingModelRemovedCarListTest() {
        for(String[] s : params) {
            ICar c = new Car(s);
            parkingModel.addCar(c);
            parkingModel.removeCar(c);
        }
        assertEquals(params.size(), parkingModel.getRemovedCarList().size());
        for(int i = 0; i < carsList.size(); i++) {
            assertEquals(carsList.get(i).license(), parkingModel.getRemovedCarList().get(i).license());
        }
    }

    @Test
    @DisplayName("tests if the car is in no list after been deleted")
    void parkingModelDeleteCarTest() {
        for(String[] s : params) {
            ICar c = new Car(s);
            parkingModel.addCar(c);
            parkingModel.deleteCar(c);
        }
        assertEquals(0, parkingModel.getCarList().size());
        assertEquals(0, parkingModel.getRemovedCarList().size());
        assertEquals(0, parkingModel.getAllCars().size());
    }

    @Test
    @DisplayName("test if removed cars are added correctly after server restart")
    void parkingModelRemoveCarRestartServerTest() {
        for(String[] s : params) {
            parkingModel.addRemovedCar(new Car(s));
        }
        assertEquals(params.size(), parkingModel.getRemovedCarList().size());
        for(int i = 0; i < carsList.size(); i++) {
            assertEquals(carsList.get(i).license(), parkingModel.getRemovedCarList().get(i).license());
        }
    }

    @Test
    @DisplayName("test if the dailyEarnings adds up the cars that left in the last 24h")
    void parkingModelDailyEarningsTest() {
        ICar x = new Car(new String[]{Data.NR, Time.now() - 10000+"",Data.DURATION,Data.PRICE,Data.TICKET,
                Data.COLOR,Data.SPACE,Data.CATEGORY,Data.TYPE,Data.LICENSE,Time.now() - 10000+""});
        parkingModel.addCar(x);
        parkingModel.removeCar(x);
        assertEquals(69, parkingModel.dailyEarnings());
        x = new Car(new String[]{Data.NR, Time.now() - Time.MILLISECONDS_PER_DAY - 6011+"",Data.DURATION,Data.PRICE,Data.TICKET,
                Data.COLOR,Data.SPACE,Data.CATEGORY,Data.TYPE,Data.LICENSE,Time.now() - Time.MILLISECONDS_PER_DAY - 6011+""});
        parkingModel.addCar(x);
        parkingModel.removeCar(x);
        assertEquals(69, parkingModel.dailyEarnings());
    }

    @Test
    @DisplayName("test if the weeklyEarnings adds up the cars that left in the last 7 days")
    void parkingModelWeeklyEarningsTest() {
        ICar x = new Car(new String[]{Data.NR, Time.now() - 10000+"",Data.DURATION,Data.PRICE,Data.TICKET,
                Data.COLOR,Data.SPACE,Data.CATEGORY,Data.TYPE,Data.LICENSE,Time.now() - 10000+""});
        parkingModel.addCar(x);
        parkingModel.removeCar(x);
        assertEquals(69, parkingModel.weeklyEarnings());
        x = new Car(new String[]{Data.NR, Time.now() - Time.MILLISECONDS_PER_WEEK - 6011+"",Data.DURATION,Data.PRICE,Data.TICKET,
                Data.COLOR,Data.SPACE,Data.CATEGORY,Data.TYPE,Data.LICENSE,Time.now() - Time.MILLISECONDS_PER_WEEK - 6011+""});
        parkingModel.addCar(x);
        parkingModel.removeCar(x);
        assertEquals(69, parkingModel.weeklyEarnings());
    }

    @RepeatedTest(10)
    @DisplayName("test if the diff between the price of the and the currentCost is less then 100 because of the delay")
    void parkingModelCurrentCostTest() {
        for(ICar car : carsNoDurationList) {
            parkingModel.addCar(car);
            assertTrue(Math.abs(car.price() - parkingModel.currentCost().get(car.license())) <= 100);
        }
    }

    @Test
    @DisplayName("test if getAllCars returns a list with added and removed cars")
    void parkingModelGetCarsTest() {
        for(String[] s : params) {
            ICar c = new Car(s);
            parkingModel.addCar(c);
            if(c.nr() % 2 == 0) parkingModel.removeCar(c);
        }
        assertEquals(params.size(), parkingModel.getAllCars().size());
    }
}
