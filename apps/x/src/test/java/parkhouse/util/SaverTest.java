package parkhouse.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import parkhouse.Data;
import parkhouse.car.ICar;

import parkhouse.controller.IParkingController;
import parkhouse.controller.ParkingController;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SaverTest {

    /*
    Author: jstueh2s & docsof2s
     */

    private IParkingController controller;
    private Saver saver = new Saver("TestServlet");
    private final List<ICar> cars = Data.cars();

    @BeforeEach
    public void setup() {
        controller = Data.controller();
        saver = new Saver("TestServlet");
    }

    @Test
    @DisplayName("Test if cars are saved and loaded correctly")
    public void saverSaveLoadCarsTest() {
        saver.saveCars(controller);
        controller = new ParkingController();
        saver.loadCars(controller);
        for (ICar c : cars) {
            assertEquals(c.toString(),
                    Finder.findCar(controller.getAllCars(), ICar::ticket, c.ticket()).toString()
            );
        }
        saver = new Saver("x");
        saver.loadCars(controller);
    }

    @Test
    @DisplayName("Test init")
    public void saverInitTest() {
        assertTrue(saver.init());
        if (saver.init()) {
            fail();
        }
        assertFalse(saver.init());
    }

    @Test
    @DisplayName("Test initConfig")
    public void saverInitConfigTest() {
        assertTrue(saver.initConfig());
        if (saver.initConfig()) {
            fail();
        }
        assertFalse(saver.initConfig());
    }

    @Test
    @DisplayName("Test if config is saved and loaded correctly")
    public void saverSaveConfig() {
        int[] config = new int[]{8, 4, 2};
        saver.saveConfig(config);
        config = saver.loadConfig();
        assertEquals(8, config[0]);
        assertEquals(4, config[1]);
        assertEquals(2, config[2]);

        saver = new Saver("x");
        saver.loadConfig();
    }

}