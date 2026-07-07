package parkhouse.views;

import parkhouse.car.ICar;
import parkhouse.models.IParkingModel;
import parkhouse.util.Jsonify;

import javax.json.JsonObject;
import java.util.List;

public class VehicleTypesView implements IObserver {

    private final IParkingModel model;
    private List<ICar> cars;

    /*
    Author: jstueh2s
     */

    public VehicleTypesView(IParkingModel model) {
        this.model = model;
        model.registerObserver(this);
    }

    @Override
    public void update() {
        this.cars = model.getCarList();
    }

    @Override
    public String toString() {
        JsonObject types = Jsonify.carsCount(cars, ICar::type);
        return Jsonify.plot(
                Jsonify.getKeys(types),
                Jsonify.getValues(types),
                "bar", "Types").toString();
    }

}
