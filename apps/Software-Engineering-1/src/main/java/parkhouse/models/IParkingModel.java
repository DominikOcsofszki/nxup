package parkhouse.models;

import parkhouse.car.ICar;

import java.util.HashMap;
import java.util.List;

public interface IParkingModel extends IObservable {

    /*
    Author: jstueh2s & docsof2s & tpapen2s
     */

    void addCar(ICar car);
    void removeCar(ICar car);
    void deleteCar(ICar car);

    void addRemovedCar(ICar car);

    List<ICar> getCarList();
    List<ICar> getRemovedCarList();
    List<ICar> getAllCars();

    long dailyEarnings();
    long weeklyEarnings();
    HashMap<String,Long> currentCost();

}
