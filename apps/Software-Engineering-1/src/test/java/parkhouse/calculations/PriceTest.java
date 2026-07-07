package parkhouse.calculations;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import parkhouse.Data;
import parkhouse.car.ICar;


import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PriceTest {

    /*
    Author: jstueh2s
     */

    private static final String FAMILY = "Family";
    private static final String SUV = "SUV";

    private final List<ICar> cars = Data.cars();

    @Test
    @DisplayName("Test if price string is formatted correctly")
    public void priceFormatTest() {
        for (ICar c : cars) {
            long price = c.price();
            assertEquals(
                    String.format(Locale.US, "%.2f€", price / 100d),
                    Price.format(price)
            );
        }
    }

    @Test
    @DisplayName("Test if price factor is parsed correctly")
    public void pricePriceFactorTest() {
        for (ICar c : cars) {
            if (c.category().equals(FAMILY) && c.type().equals(SUV)) {
                assertEquals(1.2, Price.priceFactor(c));
            } else if (c.category().equals(FAMILY) && c.type().equals("PICKUP")) {
                assertEquals(1.3, Price.priceFactor(c));
            } else if (c.category().equals("Business") && c.type().equals(SUV)) {
                assertEquals(1.1, Price.priceFactor(c));
            }else if (c.category().equals(FAMILY)) {
                assertEquals(0.5, Price.priceFactor(c));
            } else if (c.type().equals(SUV)) {
                assertEquals(2, Price.priceFactor(c));
            }
        }
    }

}
