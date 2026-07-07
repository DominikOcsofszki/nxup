package parkhouse.util;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class TimeTest {

    /*
    Author: staher2s
     */

    @Test
    void timeDifferenceTest() {
        assertEquals(0, Time.diff(50, 50));
        assertEquals(100, Time.diff(50, -50));
        assertEquals(200, Time.diff(50, -150));
        assertEquals(0, Time.diff(-50, -50));
        assertEquals(100, Time.diff(-50, 50));
        assertEquals(200, Time.diff(-150, 50));
    }

}
