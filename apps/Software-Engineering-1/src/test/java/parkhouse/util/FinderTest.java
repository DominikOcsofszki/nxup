package parkhouse.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import parkhouse.Data;
import parkhouse.car.ICar;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FinderTest {

    /*
    Author: jstueh2s
     */

    private final List<ICar> cars = Data.cars();

    @Test
    @DisplayName("Test if correct car is found by nr")
    public void finderFindCarByNrTest() {
        for (ICar c : cars) {
            assertEquals(c, Finder.findCar(cars, ICar::nr, c.nr()));
        }
    }

    @Test
    @DisplayName("Test if correct car is found by license")
    public void finderFindCarByLicenseTest() {
        for (ICar c : cars) {
            assertEquals(c, Finder.findCar(cars, ICar::license, c.license()));
        }
    }

    @Test
    @DisplayName("Test if method throws the expected exception")
    public void finderFindExceptionTest() {
        assertThrows(NoSuchElementException.class, () -> Finder.findCar(cars, ICar::nr, -1));
    }
}
