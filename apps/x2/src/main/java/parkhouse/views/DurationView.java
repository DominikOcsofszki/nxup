package parkhouse.views;

import parkhouse.car.ICar;
import parkhouse.models.IParkingModel;
import parkhouse.util.Jsonify;

import java.util.List;

public class DurationView implements IObserver {

    private final IParkingModel model;
    private List<ICar> cars;

    /*
    Author: jstueh2s
     */

    public DurationView(IParkingModel model) {
        this.model = model;
        model.registerObserver(this);
    }

    @Override
    public void update() {
        this.cars = model.getCarList();
    }

    @Override
    public String toString() {
        return Jsonify.plot(
                Jsonify.carsAsJsonArray(cars, ICar::license),
                Jsonify.carsAsJsonArray(cars, ICar::duration),
                "bar", "Duration").toString();
    }

}
