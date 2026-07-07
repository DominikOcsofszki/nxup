package parkhouse.views;

import parkhouse.calculations.Price;
import parkhouse.models.IParkingModel;

public class WeeklyEarningsView implements IObserver{

    private final IParkingModel model;
    private long weeklyEarnings;

    /*
    Author: jstueh2s
     */

    public WeeklyEarningsView(IParkingModel model) {
        this.model = model;
        model.registerObserver(this);
    }

    public long getWeeklyEarnings() {
        return weeklyEarnings;
    }

    @Override
    public void update() {
        weeklyEarnings = model.weeklyEarnings();
    }

    @Override
    public String toString() {
        return Price.format(weeklyEarnings);
    }

}
