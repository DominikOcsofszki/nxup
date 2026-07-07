package parkhouse.commands;

import parkhouse.car.ICar;
import parkhouse.controller.IParkingController;

public class CarLeaveCommand implements ICommand {

    private final ICar car;
    private final IParkingController controller;

    /*
    Author: jstueh2s
     */

    public CarLeaveCommand(ICar car, IParkingController controller) {
        this.car = car;
        this.controller = controller;
    }

    @Override
    public void execute() {
        controller.removeCar(car);
    }

    @Override
    public void undo() {
        controller.deleteCar(car);
        car.updateParams(new String[]{
                String.valueOf(car.nr()),
                String.valueOf(car.timer()),
                "_", "_", car.ticket(), car.color(),
                String.valueOf(car.space()),
                car.category(), car.type(), car.license(),
                String.valueOf(car.begin())
        });
        controller.addCar(car);
    }

}
