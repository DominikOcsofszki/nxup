package parkhouse.views;

import parkhouse.car.Car;
import parkhouse.car.ICar;
import parkhouse.models.IParkingModel;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import parkhouse.models.ParkingModel;
import parkhouse.util.Time;

public class DailyEarningsViewTest {

    /*
    Author: tpapen2s
     */

    private IParkingModel parkingModel;
    private DailyEarningsView dailyEarningsView;

    private final ICar car = new Car(new String[]{"25", Time.now() - 10000+"","6010","69","a7aa53882766f4bf361ca339fb843fa9",
            "#42671f","2","Women","SUV","SU-K 41",Time.now() - 10000+""});

    @BeforeEach
    void setup() {
        parkingModel = new ParkingModel();
        dailyEarningsView = new DailyEarningsView(parkingModel);
    }

    @AfterEach
    void teardown() {
        parkingModel = null;
        dailyEarningsView = null;
    }

    @Test
    @DisplayName("test if the dailyEarningsView gets updated")
    void dailyEarningViewGetDailyEarningsTest() {
        parkingModel.registerObserver(dailyEarningsView);
        parkingModel.addCar(car);
        parkingModel.removeCar(car);
        assertEquals(69, dailyEarningsView.getDailyEarnings());
    }

    @Test
    @DisplayName("test if the format is right")
    void dailyEarningsViewToStringTest() {
        parkingModel.registerObserver(dailyEarningsView);
        parkingModel.addCar(car);
        parkingModel.removeCar(car);
        assertEquals("0.69€", dailyEarningsView.toString());
    }

}
