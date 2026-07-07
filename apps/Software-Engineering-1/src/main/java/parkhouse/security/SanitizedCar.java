package parkhouse.security;

import parkhouse.car.CarDecorator;
import parkhouse.car.ICar;

public class SanitizedCar extends CarDecorator {

    /*
    Author: jstueh2s
     */

    public SanitizedCar(ICar car) {
        super(car);
    }

    @Override
    public String ticket() {
        return super.ticket().replaceAll("[^a-z0-9]+", "");
    }

    @Override
    public String color() {
        return super.color().replaceAll("[^a-z0-9#]+", "");
    }

    @Override
    public String category() {
        return super.category().replaceAll("[^a-zA-Z]+", "");
    }

    @Override
    public String type() {
        return super.type().replaceAll("[^a-zA-Z]+", "");
    }

    @Override
    public String license() {
        return super.license().replaceAll("[^A-Z0-9- ]+", "");
    }

    @Override
    public String toString(){
        if (super.gone()) {
            return String.format("%d/%d/%d/%d/%s/%s/%d/%s/%s/%s/%d",
                    super.nr(), super.timer(), super.duration(), super.price(), ticket(),
                    color(), super.space(), category(), type(), license(), super.begin());
        }
        return String.format("%d/%d/%s/%s/%s/%s/%d/%s/%s/%s/%d",
                super.nr(), super.timer(), "_", "_", ticket(), color(), super.space(),
                category(), type(), license(), super.begin());
    }

}
