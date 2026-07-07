package parkhouse.commands;

import org.junit.jupiter.api.*;
import parkhouse.Data;
import parkhouse.car.Car;
import parkhouse.car.ICar;
import parkhouse.controller.IParkingController;
import parkhouse.controller.ParkingController;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CommanderTest {

    /*
    Author: tpapen2s
     */

    private Commander commander;
    private final List<String[]> params = Data.paramsDuration();
    private IParkingController parkingController;

    @BeforeEach
    void setup() {
        commander = new Commander();
        parkingController = new ParkingController();
    }

    @AfterEach
    void teardown() {
        commander = null;
        parkingController = null;
    }

    @Test
    @DisplayName("tests if queue change the carLists")
    void commanderQueueTest() {
        ICar car  = new Car(params.get(0));
        commander.queue(new CarEnterCommand(car, parkingController));
        assertEquals(0, parkingController.getCars().size());
    }

    @Test
    @DisplayName("tests both activate conditions")
    void commanderActivateTest() {
        ICar car  = new Car(params.get(0));
        commander.queue(new CarEnterCommand(car, parkingController));
        commander.activate();
        assertEquals(1, parkingController.getCars().size());
        commander.activate();
        assertEquals(1, parkingController.getCars().size());
    }

    @Test
    @DisplayName("tests both undo conditions")
    void commanderUndoTest() {
        ICar car  = new Car(params.get(0));
        commander.queue(new CarEnterCommand(car, parkingController));
        commander.activate();
        commander.undo();
        assertEquals(0, parkingController.getCars().size());
        commander.undo();
        assertEquals(0, parkingController.getCars().size());
    }

}
