package parkhouse.car;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


public class CarTypesTest {

    /*
    Author: staher2s
     */

    private static final String PKW = "pkw";
    private static final String QUAD = "quad";
    private static final String TRIKE = "trike";
    private static final String PICKUP = "pickup";

    private CarTypes typePkw;
    private CarTypes typeQuad;
    private CarTypes typeTrike;
    private CarTypes typePickup;

    @BeforeEach
    void setup(){
        typePkw = CarTypes.getInstance(PKW);
        typeQuad = CarTypes.getInstance(QUAD);
        typeTrike = CarTypes.getInstance(TRIKE);
        typePickup = CarTypes.getInstance(PICKUP);
    }

    @Test
    @DisplayName("getInstance: returns the instance back")
    void getInstanceReturnsSameInstanceInstanceIsTheSame(){
        Assertions.assertEquals(typePkw, CarTypes.getInstance(PKW));
        Assertions.assertEquals(typeQuad, CarTypes.getInstance(QUAD));
        Assertions.assertEquals(typeTrike, CarTypes.getInstance(TRIKE));
        Assertions.assertEquals(typePickup, CarTypes.getInstance(PICKUP));

    }
    @Test
    @DisplayName("getInstance: not the same type")
    void getInstanceReturnsSameInstanceInstanceNotTheSame() {
        Assertions.assertNotEquals(typePkw, CarTypes.getInstance(QUAD));
        Assertions.assertNotEquals(typeQuad, CarTypes.getInstance(PKW));
        Assertions.assertNotEquals(typeTrike, CarTypes.getInstance(PICKUP));
        Assertions.assertNotEquals(typePickup, CarTypes.getInstance(TRIKE));

        Assertions.assertNotEquals(typePkw, CarTypes.getInstance(PICKUP));
        Assertions.assertNotEquals(typeQuad, CarTypes.getInstance(TRIKE));
        Assertions.assertNotEquals(typeTrike, CarTypes.getInstance(PKW));
        Assertions.assertNotEquals(typePickup, CarTypes.getInstance(QUAD));

        Assertions.assertNotEquals(typePkw, CarTypes.getInstance(TRIKE));
        Assertions.assertNotEquals(typeQuad, CarTypes.getInstance(PICKUP));
        Assertions.assertNotEquals(typeTrike, CarTypes.getInstance(QUAD));
        Assertions.assertNotEquals(typePickup, CarTypes.getInstance(PKW));

       }

    @Test
    @DisplayName("get/setFactor:sets and returns expectet value ")
    void getFactorReturnsExpectetValueIsTheSame(){
        typePkw.setFactor(2.0);
        Assertions.assertEquals(typePkw.getFactor(),2.0);
    }
    @Test
    @DisplayName("get/setFactor: check for illegalArgumentsException")
    void getFactorCheckIfExpectetValueIsTheSame(){
        Assertions.assertThrows(IllegalArgumentException.class,()-> typePkw.setFactor(0));
        Assertions.assertThrows(IllegalArgumentException.class,()-> typePkw.setFactor(-1));
    }
}