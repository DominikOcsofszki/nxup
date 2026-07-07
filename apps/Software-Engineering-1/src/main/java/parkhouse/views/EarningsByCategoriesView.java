package parkhouse.views;

import parkhouse.calculations.Price;
import parkhouse.calculations.Stats;
import parkhouse.car.ICar;
import parkhouse.config.Config;
import parkhouse.models.IParkingModel;
import parkhouse.util.Tableize;

import java.util.List;
import java.util.stream.Collectors;

public class EarningsByCategoriesView implements IObserver {

    private final IParkingModel model;
    private List<ICar> cars;

    /*
    Author: jstueh2s
     */

    public EarningsByCategoriesView(IParkingModel model) {
        this.model = model;
        model.registerObserver(this);
    }

    @Override
    public void update() {
        this.cars = model.getRemovedCarList();
    }

    @Override
    public String toString() {
        String[][] rows = new String[Config.clientCategories().length][5];
        for (int i = 0; i < rows.length; i++) {
            String category = Config.clientCategories()[i];
            List<ICar> cat = cars.stream()
                    .filter(c -> c.category().equals(category))
                    .collect(Collectors.toList());
            rows[i][0] = category;
            rows[i][1] = Price.format(Stats.sumCars(cat));
            rows[i][2] = Price.format(Stats.avgCars(cat));
            rows[i][3] = Price.format(Stats.minCars(cat));
            rows[i][4] = Price.format(Stats.maxCars(cat));
        }
        return Tableize.table(
                new String[]{"Category", "Sum", "Average", "Minimum", "Maximum"}, rows
        );
    }

}
