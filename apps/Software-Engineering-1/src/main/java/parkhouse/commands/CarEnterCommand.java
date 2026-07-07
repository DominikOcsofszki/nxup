package parkhouse.commands;

import parkhouse.car.ICar;
import parkhouse.controller.IParkingController;

public class CarEnterCommand implements ICommand {

    private final ICar car;
    private final IParkingController controller;

    /*
    Author: tpapen2s
     */

    public CarEnterCommand(ICar car, IParkingController controller) {
        this.car = car;
        this.controller = controller;
    }

    @Override
    public void execute() {
        controller.addCar(car);
    }

    @Override
    public void undo() {
        controller.deleteCar(car);
    }

}
